package com.dangod.model.entity.g;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "G_EMPINFO")
public class EmpInfo {
  @Id
  private String empid;
  private String empname;
  private String empsex;
  private String icid;
  private String deptid;
  private String optid;
  private java.sql.Date modifytime;
  private String emptype;
  private String remark;
  private String password;
  private String otherno;
  private java.sql.Date birthday;
  private String pinyin;
  private String phone;
  private String isadmin;
  private String smstype;
  private String leaderphone;
  private String otherdept;

  public String getEmpid() {
    return empid;
  }

  public String getEmpname() {
    return empname;
  }

  public String getEmpsex() {
    return empsex;
  }

  public String getIcid() {
    return icid;
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

  public String getEmptype() {
    return emptype;
  }

  public String getRemark() {
    return remark;
  }

  public String getPassword() {
    return password;
  }

  public String getOtherno() {
    return otherno;
  }

  public Date getBirthday() {
    return birthday;
  }

  public String getPinyin() {
    return pinyin;
  }

  public String getPhone() {
    return phone;
  }

  public String getIsadmin() {
    return isadmin;
  }

  public String getSmstype() {
    return smstype;
  }

  public String getLeaderphone() {
    return leaderphone;
  }

  public String getOtherdept() {
    return otherdept;
  }
}
