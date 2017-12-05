package com.dangod.dao.pi;

import com.dangod.model.entity.pi.GPSArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Yxm on 2017/11/24.
 */
public interface GPSAreaRepo extends JpaRepository<GPSArea, String> {
    //TODO JPA查询直接返回AreaId
    public List<GPSArea> findAllByDeptid(String deptId);
}
