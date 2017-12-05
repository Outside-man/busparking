package com.dangod.model.primary_key;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by Yxm on 2017/11/28.
 */
@Embeddable
public class DictPK implements Serializable {
    private String dicname;
    private String itemid;

    public String getDicname() {
        return dicname;
    }

    public void setDicname(String dicname) {
        this.dicname = dicname;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }
}
