package com.dangod.model.entity.pi;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PI_BUSACTIONLOG")
public class BusActionLog {
  @Id
  private String recid;
  private java.sql.Date actiontime;
  private String actiontype;
  private String remark;
  private String busid;
  private String deptid;
  private String place;

  public String getRecid() {
    return recid;
  }

  public void setRecid(String recid) {
    this.recid = recid;
  }

  public java.sql.Date getActiontime() {
    return actiontime;
  }

  public void setActiontime(java.sql.Date actiontime) {
    this.actiontime = actiontime;
  }

  public String getActiontype() {
    return actiontype;
  }

  public void setActiontype(String actiontype) {
    this.actiontype = actiontype;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getBusid() {
    return busid;
  }

  public void setBusid(String busid) {
    this.busid = busid;
  }

  public String getDeptid() {
    return deptid;
  }

  public void setDeptid(String deptid) {
    this.deptid = deptid;
  }

  public String getPlace() {
    return place;
  }

  public void setPlace(String place) {
    this.place = place;
  }
}
