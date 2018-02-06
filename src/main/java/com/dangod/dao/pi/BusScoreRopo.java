package com.dangod.dao.pi;

import com.dangod.model.entity.pi.BusScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

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

}
