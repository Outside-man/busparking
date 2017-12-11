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

  public String getDicid() {
    return dicid;
  }

  public String getItemvalue() {
    return itemvalue;
  }

  public String getItemnote() {
    return itemnote;
  }

  public String getItemflag() {
    return itemflag;
  }

  public String getDictype() {
    return dictype;
  }

  public String getItemindex() {
    return itemindex;
  }
}
