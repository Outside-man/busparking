package com.dangod.model.entity.g;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "G_BUSINFO")
public class BusInfo {
  @Id
  private String busid;
  private String busno;
  private String bustype;
  private String deptid;
  private String optid;
  private java.sql.Date modifytime;
  private String remark;
  private String oiltype;

  public String getBusid() {
    return busid;
  }

  public String getBusno() {
    return busno;
  }

  public String getBustype() {
    return bustype;
  }

  public String getDeptid() {
    return deptid;
  }

  public String getOptid() {
    return optid;
  }

  public Date getModifytime() {
    return modifytime;
  }

  public String getRemark() {
    return remark;
  }

  public String getOiltype() {
    return oiltype;
  }
}
