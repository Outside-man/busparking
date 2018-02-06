package com.dangod.model.entity.pi;

import com.dangod.model.primary_key.ParkdiaryPK;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "PI_PARKDIARY")
public class Parkdiary {
  @EmbeddedId
  private ParkdiaryPK parkdiaryPK;
  private String outnum_plan;
  private String outnum_real;
  private String outnum_not;
  private String outnum_late;
  private String regnum_plan;
  private String regnum_real;
  private String regnum_not;
  private String regnum_late;
  private String checknum_out;
  private String checknum_in;
  private String checknum_except;
  private String checknum_outnopass;
  private String checknum_unout;
  private String checknum_unin;
  private String collectnum_plan;
  private String collectnum_real;
  private String collectnum_not;
  private String collectnum_nosame;
  private String washnum_plan;
  private String washnum_real;
  private String parknum_plan;
  private String parknum_real;
  private String oilnum;
  private java.util.Date modifytime;
  private String carfaultnum;
  private String parknum_not;
  private String parknum_error;
  private String checknum_incomplete;
  private String outnum_norepair;
  private String parknum_other;
  private String washnum_not;
  private String peoplenum;

  public ParkdiaryPK getParkdiaryPK() {
    return parkdiaryPK;
  }

  public String getOutnum_plan() {
    return outnum_plan;
  }

  public String getOutnum_real() {
    return outnum_real;
  }

  public String getOutnum_not() {
    return outnum_not;
  }

  public String getOutnum_late() {
    return outnum_late;
  }

  public String getRegnum_plan() {
    return regnum_plan;
  }

  public String getRegnum_real() {
    return regnum_real;
  }

  public String getRegnum_not() {
    return regnum_not;
  }

  public String getRegnum_late() {
    return regnum_late;
  }

  public String getChecknum_out() {
    return checknum_out;
  }

  public String getChecknum_in() {
    return checknum_in;
  }

  public String getChecknum_except() {
    return checknum_except;
  }

  public String getChecknum_outnopass() {
    return checknum_outnopass;
  }

  public String getChecknum_unout() {
    return checknum_unout;
  }

  public String getChecknum_unin() {
    return checknum_unin;
  }

  public String getCollectnum_plan() {
    return collectnum_plan;
  }

  public String getCollectnum_real() {
    return collectnum_real;
  }

  public String getCollectnum_not() {
    return collectnum_not;
  }

  public String getCollectnum_nosame() {
    return collectnum_nosame;
  }

  public String getWashnum_plan() {
    return washnum_plan;
  }

  public String getWashnum_real() {
    return washnum_real;
  }

  public String getParknum_plan() {
    return parknum_plan;
  }

  public String getParknum_real() {
    return parknum_real;
  }

  public String getOilnum() {
    return oilnum;
  }

  public Date getModifytime() {
    return modifytime;
  }

  public String getCarfaultnum() {
    return carfaultnum;
  }

  public String getParknum_not() {
    return parknum_not;
  }

  public String getParknum_error() {
    return parknum_error;
  }

  public String getChecknum_incomplete() {
    return checknum_incomplete;
  }

  public String getOutnum_norepair() {
    return outnum_norepair;
  }

  public String getParknum_other() {
    return parknum_other;
  }

  public String getWashnum_not() {
    return washnum_not;
  }

  public String getPeoplenum() {
    return peoplenum;
  }
}
