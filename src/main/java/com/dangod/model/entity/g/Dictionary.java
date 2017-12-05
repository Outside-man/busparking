package com.dangod.model.entity.g;

import com.dangod.model.primary_key.DictPK;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "G_DICT")
public class Dictionary {
  @EmbeddedId
  private DictPK dictPK;
  private String dicid;
  private String itemvalue;
  private String itemnote;
  private String itemflag;
  private String dictype;
  private String itemindex;

  public DictPK getDictPK() {
    return dictPK;
  }

  public void setDictPK(DictPK dictPK) {
    this.dictPK = dictPK;
  }

  public String getDicid() {
    return dicid;
  }

  public void setDicid(String dicid) {
    this.dicid = dicid;
  }

  public String getItemvalue() {
    return itemvalue;
  }

  public void setItemvalue(String itemvalue) {
    this.itemvalue = itemvalue;
  }

  public String getItemnote() {
    return itemnote;
  }

  public void setItemnote(String itemnote) {
    this.itemnote = itemnote;
  }

  public String getItemflag() {
    return itemflag;
  }

  public void setItemflag(String itemflag) {
    this.itemflag = itemflag;
  }

  public String getDictype() {
    return dictype;
  }

  public void setDictype(String dictype) {
    this.dictype = dictype;
  }

  public String getItemindex() {
    return itemindex;
  }

  public void setItemindex(String itemindex) {
    this.itemindex = itemindex;
  }
}
