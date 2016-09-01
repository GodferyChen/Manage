package com.github.chen.library;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class JSONHelper {

    public static JSONObject toJSONObject(HashMap<String,Object> map) throws JSONException{
        JSONObject object = new JSONObject();
        for(String key : map.keySet()){
            object.put(key,map.get(key));
        }
        return object;
    }

    /**
     * 把一个JSONArray转化成List，其中JSONArray的子项是JSONObject
     * @param array
     * @return
     */
    public static List<HashMap<String,Object>> toMapList(JSONArray array){
        List<HashMap<String,Object>> data = new ArrayList<>();
        if(array != null && array.length() > 0){
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.optJSONObject(i);
                HashMap<String,Object> map = new HashMap<>();
                Iterator<String> it = object.keys();
                while (it.hasNext()){
                    String key = it.next();
                    map.put(key,object.opt(key));
                }
                data.add(map);
            }
        }
        return data;
    }

}
