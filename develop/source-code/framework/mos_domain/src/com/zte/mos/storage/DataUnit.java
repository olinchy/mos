package com.zte.mos.storage;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

import java.util.HashMap;
import java.util.Map;


@Entity
public class DataUnit {
    @PrimaryKey
    private String primaryKey;

    private HashMap<String, String> data = new HashMap<String, String>();

    public DataUnit() {
    }

    public DataUnit(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public void put(String key, String value){
        data.put(key, value);
    }

    public void putAll(Map<String, String> map){
        for (String key : map.keySet()){
            data.put(key, map.get(key));
        }
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public HashMap<String, String> getData(){
        return data;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder(512);
        builder.append("primaryKey=").append(primaryKey);
        for (String key : data.keySet()){
            builder.append(", ").append(key).append("=").append(data.get(key));
        }
        return builder.toString();
    }
}
