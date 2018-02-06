package com.dangod.service.impl;

import com.dangod.dao.g.DeptInfoRepo;
import com.dangod.dao.pi.GPSAreaRepo;
import com.dangod.dao.pi.ParkdiaryRepo;
import com.dangod.model.bo.GPSBO;
import com.dangod.model.bo.ParkAreaBO;
import com.dangod.model.bo.ParkBO;
import com.dangod.model.bo.ParkInfoBo;
import com.dangod.model.entity.g.DeptInfo;
import com.dangod.model.entity.pi.GPSArea;
import com.dangod.model.entity.pi.Parkdiary;
import com.dangod.model.primary_key.ParkdiaryPK;
import com.dangod.service.DictionaryService;
import com.dangod.service.ParkService;
import com.dangod.util.BaiduGPSConverter;
import com.dangod.util.GPSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Yxm on 2017/11/24.
 */
@Service
public class ParkServiceImpl implements ParkService {
    @Autowired
    private DeptInfoRepo deptInfoRepo;
    @Autowired
    private GPSAreaRepo gpsAreaRepo;
    @Autowired
    private ParkdiaryRepo parkdiaryRepo;
    @Autowired
    private DictionaryService dictionaryService;

    @Override
    public ParkBO getParkById(String deptId){
        DeptInfo deptInfo = deptInfoRepo.getOne(deptId);
        double busGPSDTO[] = new double[2];
        List<GPSBO> fence = GPSUtil.stringToGPSBO(deptInfo.getParkpoints());
        List<GPSArea> gpsAreaList = gpsAreaRepo.findAllByDeptid(deptId);
        List<ParkAreaBO> areaList = new ArrayList<>();

        for(GPSBO gpsbo : fence){
            busGPSDTO = BaiduGPSConverter.wgs84tobd09(gpsbo.getX(),gpsbo.getY());
            gpsbo.setX(busGPSDTO[0]);
            gpsbo.setY(busGPSDTO[1]);
        }

        Map<String, String> m = dictionaryService.getParkGPS(deptId);
        String center = m.get("1");
        String bg = m.get("2");
        GPSBO centerPoint = new GPSBO(center);
//        GPSBO centerPoint = new GPSBO(BaiduGPSConverter.wgs84tobd09(t.getX(), t.getY()));
        GPSBO bgPoint  = new GPSBO(bg);
//        GPSBO bgPoint = new GPSBO(BaiduGPSConverter.wgs84tobd09(t.getX(), t.getY()));


        ParkBO park = new ParkBO(centerPoint, fence, bgPoint);

        for(GPSArea area : gpsAreaList){
            List<GPSBO> areaFence = new ArrayList<>();
            areaFence = GPSUtil.stringToGPSBO(area.getPoints());
            for(GPSBO gpsbo : areaFence){
                busGPSDTO = BaiduGPSConverter.wgs84tobd09(gpsbo.getX(),gpsbo.getY());
                gpsbo.setX(busGPSDTO[0]);
                gpsbo.setY(busGPSDTO[1]);
            }
            areaList.add(new ParkAreaBO(area.getAreaname(), areaFence));
        }
        park.setParkArea(areaList);
        return park;
    }

    @Override
    public List<String> getAreaIdList(String deptId){
        List<GPSArea> areaList = gpsAreaRepo.findAllByDeptid(deptId);
        List<String> areaIdList = new ArrayList<>();
        for(GPSArea gpsArea : areaList){
            areaIdList.add(gpsArea.getAreaid());
        }
        return areaIdList;
    }

    @Override
    public ParkInfoBo getParkInfoBo(String deptId, Calendar now){
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);

        List<ParkdiaryPK> pkList = new ArrayList<>();
        pkList.add(new ParkdiaryPK(deptId, new java.sql.Date(now.getTime().getTime())));
        now.add(Calendar.DATE, -1);
        pkList.add(new ParkdiaryPK(deptId, new java.sql.Date(now.getTime().getTime())));
        now.add(Calendar.DATE, -1);
        pkList.add(new ParkdiaryPK(deptId, new java.sql.Date(now.getTime().getTime())));
        //获取近三天车场数据
        List<Parkdiary> parkdiaryList = parkdiaryRepo.findAll(pkList);

        ParkInfoBo parkInfoBo = new ParkInfoBo(parkdiaryList);

        return parkInfoBo;
    }
}
