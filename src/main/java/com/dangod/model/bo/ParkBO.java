package com.dangod.model.bo;

import java.util.List;

/**
 * Created by Yxm on 2017/11/23.
 */
public class ParkBO {
    private GPSBO centerPoint;
    private List<GPSBO> fence;

    public GPSBO getCenterPoint() {
        return centerPoint;
    }

    public void setCenterPoint(GPSBO centerPoint) {
        this.centerPoint = centerPoint;
    }

    public List<GPSBO> getFence() {
        return fence;
    }

    public void setFence(List<GPSBO> fence) {
        this.fence = fence;
    }

    public ParkBO(GPSBO centerPoint, List<GPSBO> fence) {
        this.centerPoint = centerPoint;
        this.fence = fence;
    }
}
