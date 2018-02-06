package com.dangod.model.bo;

import java.util.List;

/**
 * Created by Yxm on 2017/11/23.
 */
public class ParkBO {
    private GPSBO centerPoint;
    private GPSBO bgPoint;
    private List<GPSBO> fence;
    private List<ParkAreaBO> parkArea;

    public GPSBO getCenterPoint() {
        return centerPoint;
    }

    public void setCenterPoint(GPSBO centerPoint) {
        this.centerPoint = centerPoint;
    }

    public GPSBO getBgPoint() {
        return bgPoint;
    }

    public void setBgPoint(GPSBO bgPoint) {
        this.bgPoint = bgPoint;
    }

    public List<GPSBO> getFence() {
        return fence;
    }

    public void setFence(List<GPSBO> fence) {
        this.fence = fence;
    }

    public List<ParkAreaBO> getParkArea() {
        return parkArea;
    }

    public void setParkArea(List<ParkAreaBO> parkArea) {
        this.parkArea = parkArea;
    }

    public ParkBO(GPSBO centerPoint, List<GPSBO> fence, GPSBO bgPoint) {
        this.centerPoint = centerPoint;
        this.fence = fence;
        this.bgPoint = bgPoint;
    }
}
