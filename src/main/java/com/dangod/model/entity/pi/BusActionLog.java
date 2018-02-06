package com.dangod.model.entity.pi;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "PI_BUSACTIONLOG")
public class BusActionLog {
  @Id
  private String recid;
  private java.util.Date actiontime;
  private String actiontype;
  private String remark;
  private String busid;
  private String deptid;
  private String place;

  public String getRecid() {
    return recid;
  }

  public Date getActiontime() {
    return actiontime;
  }

  public String getActiontype() {
    return actiontype;
  }

  public String getRemark() {
    return remark;
  }

  public String getBusid() {
    return busid;
  }

  public String getDeptid() {
    return deptid;
  }

  public String getPlace() {
    return place;
  }
}
