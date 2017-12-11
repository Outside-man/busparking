package com.dangod.model.bo.ParkInfo;

import static com.dangod.model.bo.ParkInfo.ConstantInfoName.WASH;

/**
 * Created by Yxm on 2017/12/11.
 */
public class WashInfo extends BaseInfo {
    private Integer plan;
    private Integer real;
    private Integer not;

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

    public WashInfo(){
        this.setInfoName(WASH);
    }

    public WashInfo(Integer plan, Integer real, Integer not) {
        this.setInfoName(WASH);
        this.plan = plan;
        this.real = real;
        this.not = not;
    }
}
