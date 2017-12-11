package com.dangod.model.bo.ParkInfo;

import static com.dangod.model.bo.ParkInfo.ConstantInfoName.PARK;

/**
 * Created by Yxm on 2017/12/12.
 */
public class ParkInfo extends BaseInfo {
    private Integer plan;
    private Integer real;
    private Integer not;//该停车而未停车数
    private Integer error;//不该停车而停车数
    private Integer other;//非营运停车数

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

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public Integer getOther() {
        return other;
    }

    public void setOther(Integer other) {
        this.other = other;
    }

    public ParkInfo(){
        this.setInfoName(PARK);
    }

    public ParkInfo(Integer plan, Integer real, Integer not, Integer error, Integer other) {
        this.setInfoName(PARK);
        this.plan = plan;
        this.real = real;
        this.not = not;
        this.error = error;
        this.other = other;
    }
}
