package com.dangod.model.bo;

import java.sql.Timestamp;

/**
 * Created by Yxm on 2017/11/26.
 */
public class BusBO {
    private static final int MAX_ACT_STATUS = 10;
    private String lastx;
    private String lasty;
    private String busid;
    private String action;
    private Integer actstatus;
    private Integer curstatus;
    private java.sql.Timestamp lasttime;

    public String getLastx() {
        return lastx;
    }

    public void setLastx(String lastx) {
        this.lastx = lastx;
    }

    public String getLasty() {
        return lasty;
    }

    public void setLasty(String lastyY) {
        this.lasty = lastyY;
    }

    public String getBusid() {
        return busid;
    }

    public void setBusid(String busid) {
        this.busid = busid;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getActstatus() {
        return actstatus;
    }

    public void setActstatus(Integer actstatus) {
        this.actstatus = actstatus;
    }

    public Integer getCurstatus() {
        return curstatus;
    }

    public Timestamp getLasttime() {
        return lasttime;
    }

    public void setLasttime(Timestamp lastupdatetime) {
        this.lasttime = lastupdatetime;
    }

    public void setCurstatus(Integer curstatus) {
        this.curstatus = curstatus;
    }

    public BusBO() {
    }

    public BusBO(String busid, String lastx, String lasty) {
        this.lastx = lastx;
        this.lasty = lasty;
        this.busid = busid;
        this.actstatus = 0;
    }

    public Integer addActStatus(int point){
        return actstatus>=MAX_ACT_STATUS?actstatus:(actstatus=+point);
    }

    public Integer addActStatus(){
        return actstatus>=MAX_ACT_STATUS?actstatus:actstatus++;
    }
}
