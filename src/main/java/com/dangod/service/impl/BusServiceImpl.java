package com.dangod.service.impl;

import com.dangod.controller.IndexController;
import com.dangod.dao.g.BusInfoRepo;
import com.dangod.dao.g.EmpInfoRepo;
import com.dangod.dao.pi.BusActionLogRepo;
import com.dangod.dao.pi.BusGPSInfoRepo;
import com.dangod.dao.pi.BusScoreRopo;
import com.dangod.dao.pi.CheckRepo;
import com.dangod.model.entity.g.BusInfo;
import com.dangod.model.entity.g.EmpInfo;
import com.dangod.model.entity.pi.BusActionLog;
import com.dangod.model.entity.pi.BusGPSInfo;
import com.dangod.model.entity.pi.BusScore;
import com.dangod.model.entity.pi.Check;
import com.dangod.model.vo.BusInfoVO;
import com.dangod.model.vo.BusVO;
import com.dangod.service.BusService;
import com.dangod.service.DictionaryService;
import com.dangod.util.BaiduGPSConverter;
import com.dangod.util.BusStatusUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Yxm on 2017/11/24.
 */
@Service
public class BusServiceImpl implements BusService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BusGPSInfoRepo busGPSInfoRepo;
    @Autowired
    private BusActionLogRepo busActionLogRepo;
    @Autowired
    private BusScoreRopo busScoreRopo;
    @Autowired
    private BusInfoRepo busInfoRepo;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private CheckRepo checkRepo;
    @Autowired
    private EmpInfoRepo empInfoRepo;
    @Override
    public List<BusVO> getParkBusIn(String deptId){
        List<BusVO> busVOList = new ArrayList<>();
        List<BusGPSInfo> inBusList = busGPSInfoRepo.getAllBusByDeptId(deptId);
        double busGPSDTO[] = new double[2];
        for(BusGPSInfo busGPSInfo : inBusList){
            busGPSDTO = BaiduGPSConverter.wgs84tobd09(Double.parseDouble(busGPSInfo.getLastx()),Double.parseDouble(busGPSInfo.getLasty()));
            busVOList.add(new BusVO(busGPSInfo.getBusid(), Double.toString(busGPSDTO[0]), Double.toString(busGPSDTO[1])));
        }
        return busVOList;
    }
    @Override
    public BusGPSInfo getBusByBusId(String busId){
        return busGPSInfoRepo.getOne(busId);
    }

    @Override
    public List<BusActionLog> getBusActionByBusIdList(List<String> busIdList){
        return busActionLogRepo.selectBusActionByBusIdList(busIdList);
    }
    @Override
    public List<BusActionLog> getAllBusActionByBusIdList(List<String> busIdList, Calendar now, int rangeMinute){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        now.add(Calendar.MINUTE,-rangeMinute);
        String preDate = sdf.format(now.getTime());
        now.add(Calendar.MINUTE,rangeMinute*2);
        String afterDate = sdf.format(now.getTime());
        System.out.println(preDate+" "+afterDate);
        return busActionLogRepo.selectNowBusActionByBusIdList(busIdList, preDate, afterDate);
    }

    @Override
    public List<BusActionLog> getAllBusActionByDay(List<String> busIdList, Calendar now){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String start,end;
        now.set(Calendar.MINUTE,0);
        now.set(Calendar.SECOND,0);
        if(0 == now.get(Calendar.HOUR_OF_DAY)){
            now.set(Calendar.HOUR_OF_DAY,1);
            end = sdf.format(now.getTime());
            now.add(Calendar.DATE,-1);
            start = sdf.format(now.getTime());
        }else{
            now.set(Calendar.HOUR_OF_DAY,1);
            start = sdf.format(now.getTime());
            now.add(Calendar.DATE,1);
            end = sdf.format(now.getTime());
        }
        return busActionLogRepo.selectNowBusActionByBusIdList(busIdList, start, end);
    }

    @Override
    public Map<String, BusVO> getBusVO(String deptId, Map<String, BusVO> busVOMap, Calendar now){
        if(busVOMap==null) {
            busVOMap = new HashMap<>();
        }
        //如果存在已经离场的车辆 就直接移除
        Iterator<BusVO> it = busVOMap.values().iterator();
        while(it.hasNext()) {
            BusVO b = it.next();
            if(b.getCurstatus() == -1) {
                it.remove();
            }
        }
        //初始化之前车辆状态为-1(离场)
        for(String k : busVOMap.keySet()){
            busVOMap.get(k).setCurstatus(-1);
        }

        List<BusVO> busVOList =getParkBusIn(deptId);//获取在场内的车辆信息
        List<String> busIdList = new ArrayList<>();

//        for(int i = 0; i< busVOList.size(); i++){
//            /**
//             * 车辆数量随机
//             */
//            if(Math.random()>0.5){
//                busVOList.remove(i);
//            }else{
//                /**
//                 * 车辆位置随机
//                 */
//                busVOList.get(i).setLastx(Double.toString(Double.parseDouble(busVOList.get(i).getLastx())-Math.random()/10000));
//                busVOList.get(i).setLasty(Double.toString(Double.parseDouble(busVOList.get(i).getLasty())-Math.random()/10000));
//            }
//        }

        for(BusVO b : busVOList){
            if(!busVOMap.containsKey(b.getBusid())){
                //之前状态未记录的车辆 当前状态标位1(新进场)
                b.setCurstatus(1);
                busVOMap.put(b.getBusid(), b);
            }else{
                //之前状态已记录的车辆 当前状态标位0(仍在场内)
                b.setCurstatus(0);
                busVOMap.put(b.getBusid(), b);
            }
        }
        busIdList.addAll(busVOMap.keySet());
        Date d = now.getTime();
        List<BusActionLog> busActionLogList = getAllBusActionByDay(busIdList, now);
        List<BusScore> preBusScores = busScoreRopo.findAllByDeptid(deptId);//获取车场内车辆数据库里的最后一次记录的分数
        List<BusScore> scoreList = BusStatusUtil.calStatus(busActionLogList, busVOMap, d, deptId, preBusScores);

        try {
            busScoreRopo.deleteAllByDeptid(deptId);
            busScoreRopo.save(scoreList);
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        return busVOMap;
    }

    @Override
    public BusInfoVO getBusInfo(String busId){
        Map<String, String> busType = dictionaryService.getDictionary("BUSTYPE");
        Map<String, String> oilType = dictionaryService.getDictionary("OILTYPE");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

        BusInfo b = busInfoRepo.findOne(busId);
        System.out.println(busId);
        BusInfoVO v = new BusInfoVO(b.getBusid(), b.getBusno(), busType.get(b.getBustype()), b.getDeptid(), oilType.get(b.getOiltype()), b.getRemark());
        Check checkRec = checkRepo.getDriverIdByBusId(busId);
        EmpInfo driver = empInfoRepo.getOne(checkRec.getDriverid());
        v.setDriverName(driver.getEmpname());
        v.setDriverNo(driver.getEmpid());
        v.setDriverPhone(driver.getPhone());
        v.setLastCheckTime(sdf.format(checkRec.getChecktime()));
        return v;
    }


    @Override
    public List<BusGPSInfo> getAllBus(){
        return busGPSInfoRepo.findAll();
    }

    /**
     * 保持更新数据库bus分数
     */
    @Scheduled(fixedRate = 1000 * 10)
    public void keepReflash() {
        //TODO 可以在indexcontroller 中记录已经要查询的deptId 然后在方法中遍历记录值
        Date now = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        IndexController.preBusMap = this.getBusVO("090101", IndexController.preBusMap, calendar);
    }
}
