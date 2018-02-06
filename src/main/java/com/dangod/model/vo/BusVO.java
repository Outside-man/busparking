package com.dangod.model.vo;

/**
 * Created by Yxm on 2017/11/26.
 */
public class BusVO {
    private static final int MAX_ACT_STATUS = 10;
    private String lastx;
    private String lasty;
    private String busid;
    private String action;
    private Integer actstatus;
    private Integer curstatus;
//    private java.util.Date lasttime;

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

//    public Date getLasttime() {
//        return lasttime;
//    }
//
//    public void setLasttime(Date lastupdatetime) {
//        this.lasttime = lastupdatetime;
//    }

    public void setCurstatus(Integer curstatus) {
        this.curstatus = curstatus;
    }

    public BusVO() {
    }

    public BusVO(String busid, String lastx, String lasty) {
        this.lastx = lastx;
        this.lasty = lasty;
        this.busid = busid;
        this.actstatus = 0;
    }

    public Integer addActStatus(int point){
        actstatus+=point;
        if(actstatus>=MAX_ACT_STATUS)actstatus = MAX_ACT_STATUS;
        return actstatus;
    }

    public Integer addActStatus(){
        actstatus++;
        if(actstatus>=MAX_ACT_STATUS)actstatus = MAX_ACT_STATUS;
        return actstatus;
    }
}
