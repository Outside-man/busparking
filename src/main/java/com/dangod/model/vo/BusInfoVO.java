package com.dangod.model.vo;

/**
 * Created by Yxm on 2017/12/15.
 */
public class BusInfoVO {
    private String busId;
    private String busNo;//车牌号
    private String busType;//车型
    private String deptId;//所属部门
    private String oilType;//燃油类型
    private String remark;//备注
    private String driverName;
    private String driverPhone;
    private String driverNo;
    private String lastCheckTime;
    private Integer health;
//    车长信息
//    private String masterName;
//    private String masterPhone;
//    private String masterNo;

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getOilType() {
        return oilType;
    }

    public void setOilType(String oilType) {
        this.oilType = oilType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        if(driverPhone == null)
            this.driverPhone = "该司机未存储联系方式";
        else
            this.driverPhone = driverPhone;
    }

    public String getDriverNo() {
        return driverNo;
    }

    public void setDriverNo(String driverNo) {
        this.driverNo = driverNo;
    }

    public String getLastCheckTime() {
        return lastCheckTime;
    }

    public void setLastCheckTime(String lastCheckTime) {
        this.lastCheckTime = lastCheckTime;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public BusInfoVO() {
    }

    public BusInfoVO(String busId, String busNo, String busType, String deptId, String oilType, String remark) {
        this.busId = busId;
        this.busNo = busNo;
        this.busType = busType;
        this.deptId = deptId;
        this.oilType = oilType;
        this.remark = remark;
    }
}
