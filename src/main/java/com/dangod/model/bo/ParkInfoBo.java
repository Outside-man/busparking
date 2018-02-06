package com.dangod.model.bo;

import com.dangod.model.bo.ParkInfo.*;
import com.dangod.model.entity.pi.Parkdiary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dangod.model.bo.ParkInfo.ConstantParkInfo.CARFAULTNUM;
import static com.dangod.model.bo.ParkInfo.ConstantParkInfo.OILNUM;
import static com.dangod.model.bo.ParkInfo.ConstantParkInfo.PEOPLENUM;

/**
 * Created by Yxm on 2017/12/12.
 */
public class ParkInfoBo {
    private String deptId;              //停车场id
    private String modifyTime;   //记录时间
    private CheckInfo checkInfo;        //进出场安检情况   7
    private CollectInfo collectInfo;    //钱袋收取情况     4
    private ParkInfo parkInfo;          //停车情况         5
    private OutInfo outInfo;            //出车数情况       5
    private RegInfo regInfo;            //司机报道情况     4
    private WashInfo washInfo;          //洗车情况         3
    private List<Integer> peopleNum;          //场内人数
    private List<Integer> carFaultNum;        //车辆故障数
    private List<Integer> oilNum;             //加油车次
    private Map<String, String> dict;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public CheckInfo getCheckInfo() {
        return checkInfo;
    }

    public void setCheckInfo(CheckInfo checkInfo) {
        this.checkInfo = checkInfo;
    }

    public CollectInfo getCollectInfo() {
        return collectInfo;
    }

    public void setCollectInfo(CollectInfo collectInfo) {
        this.collectInfo = collectInfo;
    }

    public ParkInfo getParkInfo() {
        return parkInfo;
    }

    public void setParkInfo(ParkInfo parkInfo) {
        this.parkInfo = parkInfo;
    }

    public OutInfo getOutInfo() {
        return outInfo;
    }

    public void setOutInfo(OutInfo outInfo) {
        this.outInfo = outInfo;
    }

    public RegInfo getRegInfo() {
        return regInfo;
    }

    public void setRegInfo(RegInfo regInfo) {
        this.regInfo = regInfo;
    }

    public WashInfo getWashInfo() {
        return washInfo;
    }

    public void setWashInfo(WashInfo washInfo) {
        this.washInfo = washInfo;
    }

    public List<Integer> getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(List<Integer> peopleNum) {
        this.peopleNum = peopleNum;
    }

    public List<Integer> getCarFaultNum() {
        return carFaultNum;
    }

    public void setCarFaultNum(List<Integer> carFaultNum) {
        this.carFaultNum = carFaultNum;
    }

    public List<Integer> getOilNum() {
        return oilNum;
    }

    public Map<String, String> getDict() {
        return dict;
    }

    public void setDict(Map<String, String> dict) {
        this.dict = dict;
    }

    public void setOilNum(List<Integer> oilNum) {
        this.oilNum = oilNum;
    }

    public ParkInfoBo() {
    }

    public ParkInfoBo(List<Parkdiary> parkdiaryList) {
        Map<String, String> nameMap = new HashMap<>();
        nameMap.put("peopleNum",PEOPLENUM);
        nameMap.put("carFaultNum",CARFAULTNUM);
        nameMap.put("oilNum",OILNUM);
        this.dict = nameMap;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Parkdiary p = parkdiaryList.get(parkdiaryList.size()-1);
        this.deptId = p.getParkdiaryPK().getDeptid();
        this.modifyTime = sdf.format(p.getModifytime());
        this.checkInfo = new CheckInfo(Integer.parseInt(p.getChecknum_in()), Integer.parseInt(p.getChecknum_out()),
                Integer.parseInt(p.getChecknum_except()),Integer.parseInt(p.getChecknum_outnopass()), Integer.parseInt(p.getChecknum_unout()),
                        Integer.parseInt(p.getChecknum_unin()), Integer.parseInt(p.getChecknum_incomplete()));
        this.collectInfo = new CollectInfo(Integer.parseInt(p.getCollectnum_plan()), Integer.parseInt(p.getRegnum_real()),
                Integer.parseInt(p.getCollectnum_not()), Integer.parseInt(p.getCollectnum_nosame()));
        this.parkInfo = new ParkInfo(Integer.parseInt(p.getParknum_plan()), Integer.parseInt(p.getParknum_real()),
                Integer.parseInt(p.getParknum_not()), Integer.parseInt(p.getParknum_error()), Integer.parseInt(p.getParknum_other()));
        this.outInfo = new OutInfo(Integer.parseInt(p.getOutnum_plan()), Integer.parseInt(p.getOutnum_real()), Integer.parseInt(p.getOutnum_not()),
                Integer.parseInt(p.getOutnum_late()), Integer.parseInt(p.getOutnum_norepair()));
        this.regInfo = new RegInfo(Integer.parseInt(p.getRegnum_plan()), Integer.parseInt(p.getRegnum_real()), Integer.parseInt(p.getRegnum_not()),
                Integer.parseInt(p.getRegnum_late()));
        this.washInfo = new WashInfo(Integer.parseInt(p.getWashnum_plan()), Integer.parseInt(p.getWashnum_real()), Integer.parseInt(p.getWashnum_not()));
        List<Integer> peopleNumList = new ArrayList<>();
        List<Integer> carFaultNumList = new ArrayList<>();
        List<Integer> oilNumList = new ArrayList<>();
        for(Parkdiary k : parkdiaryList){
            peopleNumList.add(Integer.parseInt(k.getPeoplenum()));
            carFaultNumList.add(Integer.parseInt(k.getCarfaultnum()));
            oilNumList.add(Integer.parseInt(k.getOilnum()));
        }

        this.peopleNum = peopleNumList;
        this.carFaultNum = carFaultNumList;
        this.oilNum = oilNumList;
    }
}
