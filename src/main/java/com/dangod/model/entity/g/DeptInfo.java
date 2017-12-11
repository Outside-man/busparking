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

  public String getDeptname() {
    return deptname;
  }

  public String getDepttype() {
    return depttype;
  }

  public String getParentid() {
    return parentid;
  }

  public String getRemark() {
    return remark;
  }

  public String getParkpoints() {
    return parkpoints;
  }
}
