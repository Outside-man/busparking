package com.dangod.service;

import com.dangod.model.bo.ParkBO;
import com.dangod.model.bo.ParkInfoBo;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Yxm on 2017/11/23.
 */
public interface ParkService {
    /**
     * 通过deptId获取组合后的停车场信息
     * @param deptId
     * @return
     */
    public ParkBO getParkById(String deptId);

    /**
     * 通过deptId获取该停车场的所有库区AreaId
     * @param deptId
     * @return
     */
    public List<String> getAreaIdList(String deptId);

    public ParkInfoBo getParkInfoBo(String deptId, Calendar now);

}
