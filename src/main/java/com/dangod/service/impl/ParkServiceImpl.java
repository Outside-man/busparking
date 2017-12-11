package com.dangod.service.impl;

import com.dangod.dao.g.DeptInfoRepo;
import com.dangod.dao.pi.GPSAreaRepo;
import com.dangod.dao.pi.ParkdiaryRepo;
import com.dangod.model.bo.GPSBO;
import com.dangod.model.bo.ParkBO;
import com.dangod.model.bo.ParkInfoBo;
import com.dangod.model.entity.g.DeptInfo;
import com.dangod.model.entity.pi.GPSArea;
import com.dangod.model.entity.pi.Parkdiary;
import com.dangod.model.primary_key.ParkdiaryPK;
import com.dangod.service.ParkService;
import com.dangod.util.BaiduGPSConverter;
import com.dangod.util.GPSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    @Override
    public ParkBO getParkById(String deptId){
        DeptInfo deptInfo = deptInfoRepo.getOne(deptId);
        List<GPSBO> fence = GPSUtil.stringToGPSBO(deptInfo.getParkpoints());

        double busGPSDTO[] = new double[2];
        for(GPSBO gpsbo : fence){
            busGPSDTO = BaiduGPSConverter.wgs84tobd09(gpsbo.getX(),gpsbo.getY());
            gpsbo.setX(busGPSDTO[0]);
            gpsbo.setY(busGPSDTO[1]);
        }

        //TODO 通过数据库获取车库中心点
        GPSBO centerPoint = new GPSBO("120.264881, 30.449975");

        ParkBO park = new ParkBO(centerPoint, fence);
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
        now.set(Calendar.HOUR, 0);
        now.set(Calendar.MONTH, 0);
        now.set(Calendar.SECOND, 0);
        Parkdiary parkdiary = parkdiaryRepo.getOne(new ParkdiaryPK(deptId, new java.sql.Date(now.getTime().getTime())));
        ParkInfoBo parkInfoBo = new ParkInfoBo(parkdiary);
        return parkInfoBo;
    }
}
