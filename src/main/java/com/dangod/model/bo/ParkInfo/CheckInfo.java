package com.dangod.model.bo.ParkInfo;

import static com.dangod.model.bo.ParkInfo.ConstantInfoName.CHECK;

/**
 * Created by Yxm on 2017/12/11.
 */
public class CheckInfo extends BaseInfo {
    private Integer in;
    private Integer out;
    private Integer except;
    private Integer outNoPass;//出场人车不合一
    private Integer unOut;//出场未安检
    private Integer unIn;//入场未安检
    private Integer incomplete;//进场安检不到位情况

    public Integer getIn() {
        return in;
    }

    public void setIn(Integer in) {
        this.in = in;
    }

    public Integer getOut() {
        return out;
    }

    public void setOut(Integer out) {
        this.out = out;
    }

    public Integer getExcept() {
        return except;
    }

    public void setExcept(Integer except) {
        this.except = except;
    }

    public Integer getOutNoPass() {
        return outNoPass;
    }

    public void setOutNoPass(Integer outNoPass) {
        this.outNoPass = outNoPass;
    }

    public Integer getUnOut() {
        return unOut;
    }

    public void setUnOut(Integer unOut) {
        this.unOut = unOut;
    }

    public Integer getUnIn() {
        return unIn;
    }

    public void setUnIn(Integer unIn) {
        this.unIn = unIn;
    }

    public Integer getIncomplete() {
        return incomplete;
    }

    public void setIncomplete(Integer incomplete) {
        this.incomplete = incomplete;
    }

    public CheckInfo(){
        this.setInfoName(CHECK);
    }

    public CheckInfo(Integer in, Integer out, Integer except, Integer outNoPass, Integer unOut, Integer unIn, Integer incomplete) {
        this.setInfoName(CHECK);
        this.in = in;
        this.out = out;
        this.except = except;
        this.outNoPass = outNoPass;
        this.unOut = unOut;
        this.unIn = unIn;
        this.incomplete = incomplete;
    }
}
