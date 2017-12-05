package com.dangod.service.impl;

import com.dangod.dao.pi.BusActionLogRepo;
import com.dangod.dao.pi.BusGPSInfoRepo;
import com.dangod.dao.pi.BusScoreRopo;
import com.dangod.model.entity.pi.BusActionLog;
import com.dangod.model.entity.pi.BusGPSInfo;
import com.dangod.model.entity.pi.BusScore;
import com.dangod.model.vo.BusVO;
import com.dangod.service.BusService;
import com.dangod.util.BaiduGPSConverter;
import com.dangod.util.BusStatusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Yxm on 2017/11/24.
 */
@Service
public class BusServiceImpl implements BusService {
    @Autowired
    private BusGPSInfoRepo busGPSInfoRepo;
    @Autowired
    private BusActionLogRepo busActionLogRepo;
    @Autowired
    private BusScoreRopo busScoreRopo;
    @Override
    public List<BusGPSInfo> getParkBusIn(String deptId){
        List<BusGPSInfo> inBusList = busGPSInfoRepo.getAllBusByDeptId(deptId);
        double busGPSDTO[] = new double[2];
        for(BusGPSInfo busGPSInfo : inBusList){
            busGPSDTO = BaiduGPSConverter.wgs84tobd09(Double.parseDouble(busGPSInfo.getLastx()),Double.parseDouble(busGPSInfo.getLasty()));
            busGPSInfo.setLastx(Double.toString(busGPSDTO[0]));
            busGPSInfo.setLasty(Double.toString(busGPSDTO[1]));
        }
        return inBusList;
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
        if(0 == now.get(Calendar.HOUR)){
            now.set(Calendar.HOUR,1);
            end = sdf.format(now.getTime());
            now.add(Calendar.DATE,-1);
            start = sdf.format(now.getTime());
        }else{
            now.set(Calendar.HOUR,1);
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
        busVOMap.entrySet().removeIf(entry -> entry.getValue().getCurstatus() == -1);
        //初始化之前车辆状态为-1(离场)
        for(String k : busVOMap.keySet()){
            busVOMap.get(k).setCurstatus(-1);
        }

        List<BusGPSInfo> busInList = getParkBusIn(deptId);
        List<BusVO> busVOList = new ArrayList<>();
        List<String> busIdList = new ArrayList<>();

        for(int i = 0;i<busInList.size();i++){
            /**
             * 车辆数量随机
             */
//            if(Math.random()>0.9){
//                busInList.remove(i);
//            }else{
                /**
                 * 车辆位置随机
                 */
                busInList.get(i).setLastx(Double.toString(Double.parseDouble(busInList.get(i).getLastx())-Math.random()/10000));
                busInList.get(i).setLasty(Double.toString(Double.parseDouble(busInList.get(i).getLasty())-Math.random()/10000));
                busVOList.add(new BusVO(busInList.get(i).getBusid(), busInList.get(i).getLastx(), busInList.get(i).getLasty()));
//            }
        }

        for(BusVO b : busVOList){
            if(!busVOMap.containsKey(b.getBusid())){
                //之前状态未记录的车辆 当前状态标位1(新进场)
                busVOMap.put(b.getBusid(), b);
                b.setCurstatus(1);
            }else{
                //之前状态已记录的车辆 当前状态标位0(仍在场内)
                busVOMap.put(b.getBusid(), b);
                b.setCurstatus(0);
            }
        }
        busIdList.addAll(busVOMap.keySet());
        Date d = now.getTime();
        List<BusActionLog> busActionLogList = getAllBusActionByDay(busIdList, now);
        List<BusScore> preBusScores = busScoreRopo.findAllByDeptid(deptId);
        List<BusScore> scoreList = BusStatusUtil.calStatus(busActionLogList, busVOMap, d, deptId, preBusScores);
        System.out.println("================================================================");
        //TODO 保存分数表时会保存GPS表
        busScoreRopo.save(scoreList);
        System.out.println("================================================================");
        return busVOMap;
    }

    @Override
    public List<BusGPSInfo> getAllBus(){
        return busGPSInfoRepo.findAll();
    }

}
