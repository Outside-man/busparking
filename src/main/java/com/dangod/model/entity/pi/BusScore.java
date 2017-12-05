package com.dangod.model.entity.pi;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by Yxm on 2017/12/4.
 */
@Entity
@Table(name = "PI_BUSSCORE")
public class BusScore {
    @Id
    private String busid;
    private Integer score;
    private java.sql.Timestamp lasttime;
    private String deptid;

    public String getBusid() {
        return busid;
    }

    public void setBusid(String busid) {
        this.busid = busid;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Timestamp getLasttime() {
        return lasttime;
    }

    public void setLasttime(Timestamp lasttime) {
        this.lasttime = lasttime;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public BusScore() {
    }

    public BusScore(String busid, Integer score, Timestamp lasttime, String deptid) {
        this.busid = busid;
        this.score = score;
        this.lasttime = lasttime;
        this.deptid = deptid;
    }
}
