package com.dangod.model.bo.ParkInfo;

import java.util.Map;

/**
 * Created by Yxm on 2017/12/11.
 */
public abstract class BaseInfo {
    private String infoName;
    private Map<String, String> dict;
    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public Map<String, String> getDict() {
        return dict;
    }

    public void setDict(Map<String, String> dict) {
        this.dict = dict;
    }
}
