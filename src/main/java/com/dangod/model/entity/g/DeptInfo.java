package com.dangod.model.entity.g;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "G_DEPTINFO")
public class DeptInfo {
  @Id
  private String deptid;
  private String deptname;
  private String depttype;
  private String parentid;
  private String remark;
  private String parkpoints;

  public String getDeptid() {
    return deptid;
  }

  public void setDeptid(String deptid) {
    this.deptid = deptid;
  }

  public String getDeptname() {
    return deptname;
  }

  public void setDeptname(String deptname) {
    this.deptname = deptname;
  }

  public String getDepttype() {
    return depttype;
  }

  public void setDepttype(String depttype) {
    this.depttype = depttype;
  }

  public String getParentid() {
    return parentid;
  }

  public void setParentid(String parentid) {
    this.parentid = parentid;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getParkpoints() {
    return parkpoints;
  }

  public void setParkpoints(String parkpoints) {
    this.parkpoints = parkpoints;
  }
}
