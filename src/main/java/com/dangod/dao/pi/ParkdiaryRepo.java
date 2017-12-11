package com.dangod.dao.pi;

import com.dangod.model.entity.pi.Parkdiary;
import com.dangod.model.primary_key.ParkdiaryPK;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Yxm on 2017/12/11.
 */
public interface ParkdiaryRepo extends JpaRepository<Parkdiary, ParkdiaryPK> {

}
