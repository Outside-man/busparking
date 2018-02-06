package com.dangod.service;

import java.util.Map;

/**
 * Created by Yxm on 2017/11/28.
 */
public interface DictionaryService {
    public Map<String, String> getDictionary(String dicId);

    public Map<String, String> getParkGPS(String deptId);
}
