package com.dangod.dao.pi;

import com.dangod.model.entity.pi.Check;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Yxm on 2017/12/15.
 */
public interface CheckRepo extends JpaRepository<Check, String> {
    @Query(value = "select * from PI_CHECK where RECID IN (SELECT MAX(RECID) FROM PI_CHECK where BUSID = :busId)", nativeQuery=true)
    public Check getDriverIdByBusId(@Param("busId")String busId);
}
