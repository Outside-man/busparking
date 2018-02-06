package com.dangod.service.impl;

import com.dangod.dao.g.DictionaryRepo;
import com.dangod.model.entity.g.Dictionary;
import com.dangod.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yxm on 2017/11/28.
 */
@Service
public class DictionaryImpl implements DictionaryService {
    @Autowired
    private DictionaryRepo dictionaryRepo;

    @Override
    public Map<String, String> getDictionary(String dicId){
        List<Dictionary> list = dictionaryRepo.findAllByDicid(dicId);
        Map<String, String> dictionary = new HashMap<>();
        for(Dictionary d : list){
            dictionary.put(d.getDictPK().getItemid(),d.getItemvalue());
        }
        return dictionary;
    }

    @Override
    public Map<String, String> getParkGPS(String deptId){
        List<Dictionary> list = dictionaryRepo.findAllByDicid("GPSPARK");
        Map<String, String> dictionary = new HashMap<>();
        for(Dictionary d : list){
            if(d.getDictPK().getDicname().equals(deptId))
            dictionary.put(d.getDictPK().getItemid(), d.getItemvalue());
        }
        return dictionary;
    }


}
