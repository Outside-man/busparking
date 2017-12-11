package com.dangod.model.primary_key;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Yxm on 2017/11/28.
 */
@Embeddable
public class ParkdiaryPK implements Serializable {
    private java.sql.Date diarydate;
    private String deptid;

    public Date getDiarydate() {
        return diarydate;
    }

    public void setDiarydate(Date diarydate) {
        this.diarydate = diarydate;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public ParkdiaryPK(String deptid, Date diarydate) {
        this.diarydate = diarydate;
        this.deptid = deptid;
    }
}
