package com.dangod.model.bo.ParkInfo;

import java.util.HashMap;
import java.util.Map;

import static com.dangod.model.bo.ParkInfo.ConstantParkInfo.*;

/**
 * Created by Yxm on 2017/12/11.
 */
public class RegInfo extends BaseInfo {
//    private Integer plan;
    private Integer real;
    private Integer not;
    private Integer late;

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

    public Integer getLate() {
        return late;
    }

    public void setLate(Integer late) {
        this.late = late;
    }

    public RegInfo(){
        this.setInfoName(REG);
        Map<String, String> nameMap = new HashMap<>();
        nameMap.put("real",REG_REAL);
        nameMap.put("not",REG_NOT);
        nameMap.put("late",REG_LATE);
        this.setDict(nameMap);
    }

    public RegInfo(Integer plan, Integer real, Integer not, Integer late) {
        this.setInfoName(REG);
        Map<String, String> nameMap = new HashMap<>();
        nameMap.put("real",REG_REAL);
        nameMap.put("not",REG_NOT);
        nameMap.put("late",REG_LATE);
        this.setDict(nameMap);
//        this.plan = plan;
        this.real = real;
        this.not = not;
        this.late = late;
    }
}
