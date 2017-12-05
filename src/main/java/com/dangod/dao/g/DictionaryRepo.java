package com.dangod.dao.g;

import com.dangod.model.entity.g.Dictionary;
import com.dangod.model.primary_key.DictPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Yxm on 2017/11/28.
 */
public interface DictionaryRepo extends JpaRepository<Dictionary, DictPK> {
    public List<Dictionary> findAllByDicid(String dicId);
}
