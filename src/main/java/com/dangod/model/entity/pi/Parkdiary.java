package com.dangod.model.entity.pi;

import com.dangod.model.primary_key.ParkdiaryPK;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

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
  private java.sql.Date modifytime;
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

  public void setParkdiaryPK(ParkdiaryPK parkdiaryPK) {
    this.parkdiaryPK = parkdiaryPK;
  }

  public String getOutnum_plan() {
    return outnum_plan;
  }

  public void setOutnum_plan(String outnum_plan) {
    this.outnum_plan = outnum_plan;
  }

  public String getOutnum_real() {
    return outnum_real;
  }

  public void setOutnum_real(String outnum_real) {
    this.outnum_real = outnum_real;
  }

  public String getOutnum_not() {
    return outnum_not;
  }

  public void setOutnum_not(String outnum_not) {
    this.outnum_not = outnum_not;
  }

  public String getOutnum_late() {
    return outnum_late;
  }

  public void setOutnum_late(String outnum_late) {
    this.outnum_late = outnum_late;
  }

  public String getRegnum_plan() {
    return regnum_plan;
  }

  public void setRegnum_plan(String regnum_plan) {
    this.regnum_plan = regnum_plan;
  }

  public String getRegnum_real() {
    return regnum_real;
  }

  public void setRegnum_real(String regnum_real) {
    this.regnum_real = regnum_real;
  }

  public String getRegnum_not() {
    return regnum_not;
  }

  public void setRegnum_not(String regnum_not) {
    this.regnum_not = regnum_not;
  }

  public String getRegnum_late() {
    return regnum_late;
  }

  public void setRegnum_late(String regnum_late) {
    this.regnum_late = regnum_late;
  }

  public String getChecknum_out() {
    return checknum_out;
  }

  public void setChecknum_out(String checknum_out) {
    this.checknum_out = checknum_out;
  }

  public String getChecknum_in() {
    return checknum_in;
  }

  public void setChecknum_in(String checknum_in) {
    this.checknum_in = checknum_in;
  }

  public String getChecknum_except() {
    return checknum_except;
  }

  public void setChecknum_except(String checknum_except) {
    this.checknum_except = checknum_except;
  }

  public String getChecknum_outnopass() {
    return checknum_outnopass;
  }

  public void setChecknum_outnopass(String checknum_outnopass) {
    this.checknum_outnopass = checknum_outnopass;
  }

  public String getChecknum_unout() {
    return checknum_unout;
  }

  public void setChecknum_unout(String checknum_unout) {
    this.checknum_unout = checknum_unout;
  }

  public String getChecknum_unin() {
    return checknum_unin;
  }

  public void setChecknum_unin(String checknum_unin) {
    this.checknum_unin = checknum_unin;
  }

  public String getCollectnum_plan() {
    return collectnum_plan;
  }

  public void setCollectnum_plan(String collectnum_plan) {
    this.collectnum_plan = collectnum_plan;
  }

  public String getCollectnum_real() {
    return collectnum_real;
  }

  public void setCollectnum_real(String collectnum_real) {
    this.collectnum_real = collectnum_real;
  }

  public String getCollectnum_not() {
    return collectnum_not;
  }

  public void setCollectnum_not(String collectnum_not) {
    this.collectnum_not = collectnum_not;
  }

  public String getCollectnum_nosame() {
    return collectnum_nosame;
  }

  public void setCollectnum_nosame(String collectnum_nosame) {
    this.collectnum_nosame = collectnum_nosame;
  }

  public String getWashnum_plan() {
    return washnum_plan;
  }

  public void setWashnum_plan(String washnum_plan) {
    this.washnum_plan = washnum_plan;
  }

  public String getWashnum_real() {
    return washnum_real;
  }

  public void setWashnum_real(String washnum_real) {
    this.washnum_real = washnum_real;
  }

  public String getParknum_plan() {
    return parknum_plan;
  }

  public void setParknum_plan(String parknum_plan) {
    this.parknum_plan = parknum_plan;
  }

  public String getParknum_real() {
    return parknum_real;
  }

  public void setParknum_real(String parknum_real) {
    this.parknum_real = parknum_real;
  }

  public String getOilnum() {
    return oilnum;
  }

  public void setOilnum(String oilnum) {
    this.oilnum = oilnum;
  }

  public Date getModifytime() {
    return modifytime;
  }

  public void setModifytime(Date modifytime) {
    this.modifytime = modifytime;
  }

  public String getCarfaultnum() {
    return carfaultnum;
  }

  public void setCarfaultnum(String carfaultnum) {
    this.carfaultnum = carfaultnum;
  }

  public String getParknum_not() {
    return parknum_not;
  }

  public void setParknum_not(String parknum_not) {
    this.parknum_not = parknum_not;
  }

  public String getParknum_error() {
    return parknum_error;
  }

  public void setParknum_error(String parknum_error) {
    this.parknum_error = parknum_error;
  }

  public String getChecknum_incomplete() {
    return checknum_incomplete;
  }

  public void setChecknum_incomplete(String checknum_incomplete) {
    this.checknum_incomplete = checknum_incomplete;
  }

  public String getOutnum_norepair() {
    return outnum_norepair;
  }

  public void setOutnum_norepair(String outnum_norepair) {
    this.outnum_norepair = outnum_norepair;
  }

  public String getParknum_other() {
    return parknum_other;
  }

  public void setParknum_other(String parknum_other) {
    this.parknum_other = parknum_other;
  }

  public String getWashnum_not() {
    return washnum_not;
  }

  public void setWashnum_not(String washnum_not) {
    this.washnum_not = washnum_not;
  }

  public String getPeoplenum() {
    return peoplenum;
  }

  public void setPeoplenum(String peoplenum) {
    this.peoplenum = peoplenum;
  }
}
