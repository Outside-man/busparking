package com.dangod.model.entity.pi;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "PI_BUSGPSINFO")
public class BusGPSInfo {
  @Id
  private String busid;
  private String lastx;
  private String lasty;
  private java.sql.Date lasttime;
  private String areaid;


  public String getBusid() {
    return busid;
  }

  public String getLastx() {
    return lastx;
  }

  public String getLasty() {
    return lasty;
  }

  public Date getLasttime() {
    return lasttime;
  }

  public String getAreaid() {
    return areaid;
  }

  @Override
  public boolean equals(Object o) {
    System.out.println(((BusGPSInfo) o).getBusid());
    if(this==o){
      return true ;
    }
    if(!(o instanceof BusGPSInfo)){
      return false ;
    }
    BusGPSInfo that = (BusGPSInfo)o ;    // 向下转型
    String a = that.getBusid();
    String b = this.getBusid();
//    System.out.println(a);
//    System.out.println(b);
//    if(this.busid.equals(that.busid)){
//      return true ;
//    }else{
//      return false ;
//    }
    if(a.equals(b)){
      return true ;
    }else{
      return false ;
    }
  }

  @Override
  public int hashCode() {
    return busid.hashCode();
  }
}
