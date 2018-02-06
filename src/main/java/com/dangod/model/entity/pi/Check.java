package com.dangod.model.entity.pi;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Blob;
import java.util.Date;


@Entity
@Table(name = "PI_CHECK")
public class Check {
  @Id
  private String recid;
  private String busid;
  private String workerid;
  private String driverid;
  private String deptid;
  private java.util.Date checktime;
  private String fitflag;
  private String optid;
  private String remark;
  private String photoname;
  private Blob checkphoto1;
  private Blob checkphoto2;
  private Blob checkphoto3;
  private Blob checkphoto4;
  private Blob checkphoto5;
  private Blob checkphoto6;
  private String checkresult;
  private String busflagid;
  private String busidcheckflag;
  private java.sql.Date modifytime;
  private String photonum;

  public String getRecid() {
    return recid;
  }

  public String getBusid() {
    return busid;
  }

  public String getWorkerid() {
    return workerid;
  }

  public String getDriverid() {
    return driverid;
  }

  public String getDeptid() {
    return deptid;
  }

  public Date getChecktime() {
    return checktime;
  }

  public String getFitflag() {
    return fitflag;
  }

  public String getOptid() {
    return optid;
  }

  public String getRemark() {
    return remark;
  }

  public String getPhotoname() {
    return photoname;
  }

  public Blob getCheckphoto1() {
    return checkphoto1;
  }

  public Blob getCheckphoto2() {
    return checkphoto2;
  }

  public Blob getCheckphoto3() {
    return checkphoto3;
  }

  public Blob getCheckphoto4() {
    return checkphoto4;
  }

  public Blob getCheckphoto5() {
    return checkphoto5;
  }

  public Blob getCheckphoto6() {
    return checkphoto6;
  }

  public String getCheckresult() {
    return checkresult;
  }

  public String getBusflagid() {
    return busflagid;
  }

  public String getBusidcheckflag() {
    return busidcheckflag;
  }

  public java.sql.Date getModifytime() {
    return modifytime;
  }

  public String getPhotonum() {
    return photonum;
  }
}
