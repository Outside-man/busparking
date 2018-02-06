package com.dangod.model.bo;

import java.util.List;

/**
 * Created by Yxm on 2017/12/20.
 */
public class ParkAreaBO {
    private String name;
    private List<GPSBO> area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GPSBO> getArea() {
        return area;
    }

    public void setArea(List<GPSBO> area) {
        this.area = area;
    }

    public ParkAreaBO() {
    }

    public ParkAreaBO(String name, List<GPSBO> area) {
        this.name = name;
        this.area = area;
    }
}
