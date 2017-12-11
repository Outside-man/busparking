package com.dangod.model.entity.pi;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

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

  public String getAreaname() {
    return areaname;
  }

  public String getWeights() {
    return weights;
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

  public String getParknum() {
    return parknum;
  }

  public String getPoints() {
    return points;
  }
}
