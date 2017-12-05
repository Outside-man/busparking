package com.dangod.model.entity.pi;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PI_GPSAREA")
public class GPSArea {
  @Id
  private String areaid;
  private String areaname;
  private String weights;
  private String deptid;
  private String optid;
  private java.sql.Date modifytime;
  private String parknum;
  private String points;

  public String getAreaid() {
    return areaid;
  }

  public void setAreaid(String areaid) {
    this.areaid = areaid;
  }

  public String getAreaname() {
    return areaname;
  }

  public void setAreaname(String areaname) {
    this.areaname = areaname;
  }

  public String getWeights() {
    return weights;
  }

  public void setWeights(String weights) {
    this.weights = weights;
  }

  public String getDeptid() {
    return deptid;
  }

  public void setDeptid(String deptid) {
    this.deptid = deptid;
  }

  public String getOptid() {
    return optid;
  }

  public void setOptid(String optid) {
    this.optid = optid;
  }

  public java.sql.Date getModifytime() {
    return modifytime;
  }

  public void setModifytime(java.sql.Date modifytime) {
    this.modifytime = modifytime;
  }

  public String getParknum() {
    return parknum;
  }

  public void setParknum(String parknum) {
    this.parknum = parknum;
  }

  public String getPoints() {
    return points;
  }

  public void setPoints(String points) {
    this.points = points;
  }
}
