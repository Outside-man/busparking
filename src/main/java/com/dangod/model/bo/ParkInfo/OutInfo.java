package com.dangod.model.bo.ParkInfo;

import static com.dangod.model.bo.ParkInfo.ConstantInfoName.OUT;

/**
 * Created by Yxm on 2017/12/11.
 */
public class OutInfo extends BaseInfo {
    private Integer plan;
    private Integer real;
    private Integer not;
    private Integer late;
    private Integer noRepair;

    public Integer getPlan() {
        return plan;
    }

    public void setPlan(Integer plan) {
        this.plan = plan;
    }

    public Integer getReal() {
        return real;
    }

    public void setReal(Integer real) {
        this.real = real;
    }

    public Integer getNot() {
        return not;
    }

    public void setNot(Integer not) {
        this.not = not;
    }

    public Integer getLate() {
        return late;
    }

    public void setLate(Integer late) {
        this.late = late;
    }

    public OutInfo(){
        this.setInfoName(OUT);
    }

    public Integer getNoRepair() {
        return noRepair;
    }

    public void setNoRepair(Integer noRepair) {
        this.noRepair = noRepair;
    }

    public OutInfo(Integer plan, Integer real, Integer not, Integer late, Integer noRepair) {
        this.setInfoName(OUT);
        this.plan = plan;
        this.real = real;
        this.not = not;
        this.late = late;
        this.noRepair = noRepair;
    }
}
