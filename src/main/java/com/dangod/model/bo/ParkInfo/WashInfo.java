package com.dangod.model.bo.ParkInfo;

import java.util.HashMap;
import java.util.Map;

import static com.dangod.model.bo.ParkInfo.ConstantParkInfo.*;

/**
 * Created by Yxm on 2017/12/11.
 */
public class WashInfo extends BaseInfo {
//    private Integer plan;
    private Integer real;
    private Integer not;

//    public Integer getPlan() {
//        return plan;
//    }
//
//    public void setPlan(Integer plan) {
//        this.plan = plan;
//    }

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
        Map<String, String> nameMap = new HashMap<>();
        nameMap.put("real",WASH_REAL);
        nameMap.put("not",WASH_NOT);
        this.setDict(nameMap);
    }

    public WashInfo(Integer plan, Integer real, Integer not) {
        this.setInfoName(WASH);
        Map<String, String> nameMap = new HashMap<>();
        nameMap.put("real",WASH_REAL);
        nameMap.put("not",WASH_NOT);
        this.setDict(nameMap);
//        this.plan = plan;
        this.real = plan - not;
        this.not = not;
    }
}
