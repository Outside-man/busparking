package com.dangod.service;

import com.dangod.model.entity.pi.BusActionLog;
import com.dangod.model.entity.pi.BusGPSInfo;
import com.dangod.model.vo.BusVO;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Yxm on 2017/11/23.
 */
public interface BusService {
    /**
     * 通过busId获取车辆GPS信息
     * @param busId
     * @return
     */
    public BusGPSInfo getBusByBusId(String busId);

    /**
     * 根据deptId 获取在 该场内的所有车辆GPS信息
     * @param deptId
     * @return
     */
    public List<BusGPSInfo> getParkBusIn(String deptId);

    /**
     * 通过BusId列表获取列表中所有bus的Action信息
     * @param busIdList
     * @return
     */
    public List<BusActionLog> getBusActionByBusIdList(List<String> busIdList);

    /**
     * 获取时间段内的车辆的动作记录
     * @param busIdList
     * @param now
     * @param rangeMinute
     * @return
     */
    public List<BusActionLog> getAllBusActionByBusIdList(List<String> busIdList, Calendar now, int rangeMinute);

    /**
     * 获取一个工作天内(1:00-1:00)的车辆动作记录
     * @param busIdList
     * @param now
     * @return
     */
    public List<BusActionLog> getAllBusActionByDay(List<String> busIdList, Calendar now);

    public Map<String, BusVO> getBusVO(String deptId, Map<String, BusVO> preBusMap, Calendar now);

    public List<BusGPSInfo> getAllBus();

}
