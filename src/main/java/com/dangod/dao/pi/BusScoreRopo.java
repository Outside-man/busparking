package com.dangod.dao.pi;

import com.dangod.model.entity.pi.BusScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Yxm on 2017/12/4.
 */
public interface BusScoreRopo extends JpaRepository<BusScore, String> {
    public List<BusScore> findAllByDeptid(String deptId);

    @Modifying
    @Transactional
    public Long deleteAllByDeptid(String deptId);

    @Query(value = "delete from PI_BUSSCORE where BUSID IN (SELECT BUSID FROM PI_BUSSCORE WHERE DEPTID=:deptId) ", nativeQuery = true)
    public void woc(@Param("deptId")String deptId);
}
