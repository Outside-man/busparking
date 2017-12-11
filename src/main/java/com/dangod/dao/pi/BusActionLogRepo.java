package com.dangod.dao.pi;

import com.dangod.model.entity.pi.BusActionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Yxm on 2017/11/26.
 */
public interface BusActionLogRepo extends JpaRepository<BusActionLog, String>, JpaSpecificationExecutor<BusActionLog>{
    @Query(value = "select * from PI_BUSACTIONLOG WHERE RECID in (select max(RECID) from PI_BUSACTIONLOG where BUSID in :busList GROUP BY(BUSID))", nativeQuery=true)
    public List<BusActionLog> selectBusActionByBusIdList(@Param("busList")List<String> busList);

    @Query(value = "select * from PI_BUSACTIONLOG where BUSID IN :busIdList and ACTIONTIME >= TO_DATE(:preDate, 'YYYY-MM-DD hh24:mi:ss') and ACTIONTIME <= TO_DATE(:afterDate, 'YYYY-MM-DD hh24:mi:ss') ORDER BY ACTIONTIME", nativeQuery=true)
    public List<BusActionLog> selectNowBusActionByBusIdList(@Param("busIdList")List<String> busList, @Param("preDate")String preDate,  @Param("afterDate")String afterDate);
}
