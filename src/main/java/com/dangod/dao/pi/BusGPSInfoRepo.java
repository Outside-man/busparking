package com.dangod.dao.pi;

import com.dangod.model.entity.pi.BusGPSInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Yxm on 2017/11/23.
 */
public interface BusGPSInfoRepo extends JpaRepository<BusGPSInfo, String>, JpaSpecificationExecutor<BusGPSInfo> {
    public List<BusGPSInfo> findAllByAreaid(String areaId);

    @Query(value = "select * from PI_BUSGPSINFO where AREAID IN (select AREAID FROM PI_GPSAREA WHERE DEPTID = :deptId)", nativeQuery=true)
    public List<BusGPSInfo> getAllBusByDeptId(@Param("deptId")String deptId);
}
