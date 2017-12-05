package com.dangod.util;

import com.dangod.model.bo.GPSBO;
import com.dangod.model.bo.ParkBO;
import com.dangod.model.entity.pi.BusGPSInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yxm on 2017/11/23.
 */
public class GPSUtil {
    /**
     * 传入形如 xxx,xxxx\r\nxxx,xxxx 的字符串可以解析出坐标点
     * @param parkPoints
     * @return List<GPSBO>
     */
    public static List<GPSBO> stringToGPSBO(String parkPoints){
        List<GPSBO> GPSBOList = new ArrayList<>();
        String points[] =  parkPoints.split("\r\n|\r|\n");
        String p[] = new String[2];
        for(String point : points){
            GPSBOList.add(new GPSBO(point));
        }
        return GPSBOList;
    }

    public static boolean checkBusIn(ParkBO park, BusGPSInfo bus){
        List<GPSBO> fenceList = park.getFence();
        int b = 1;
        for(GPSBO g : fenceList){
            g.setX(g.getX()*b);
            g.setY(g.getY()*b);
        }
        double x = Double.parseDouble(bus.getLastx())*b;
        double y = Double.parseDouble(bus.getLasty())*b;
        int n = fenceList.size();
        int i,j;
        boolean c = false;
        for(i = 0,j = n-1;i<n;j=i,i++){
            if(((fenceList.get(i).getY()> y)!=(fenceList.get(j).getY()> y))
                    &&(x < (fenceList.get(j).getX() - fenceList.get(i).getX())
                    *(y - fenceList.get(i).getY())/(fenceList.get(j).getY() - fenceList.get(i).getY()) + fenceList.get(i).getX())){
                c = !c;
            }
        }
        return c;
    }
}
