package com.dangod.model.bo.ParkInfo;

import static com.dangod.model.bo.ParkInfo.ConstantInfoName.COLLECT;

/**
 * Created by Yxm on 2017/12/11.
 */
public class CollectInfo extends BaseInfo {
    private Integer plan;
    private Integer real;
    private Integer not;
    private Integer noSame;

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

    public Integer getNoSame() {
        return noSame;
    }

    public void setNoSame(Integer noSame) {
        this.noSame = noSame;
    }

    public CollectInfo(){
        this.setInfoName(COLLECT);
    }

    public CollectInfo(Integer plan, Integer real, Integer not, Integer noSame) {
        this.setInfoName(COLLECT);
        this.plan = plan;
        this.real = real;
        this.not = not;
        this.noSame = noSame;
    }
}
