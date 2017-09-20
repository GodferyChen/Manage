package com.github.chen.library;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class JsonHelper {

    public static JSONObject toJSONObject(HashMap<String, Object> map) throws JSONException {
        JSONObject object = new JSONObject();
        for (String key : map.keySet()) {
            object.put(key, map.get(key));
        }
        return object;
    }

    public static HashMap<String, Object> toMap(JSONObject object) {
        HashMap<String, Object> map = new HashMap<>();
        Iterator<String> it = object.keys();
        while (it.hasNext()) {
            String key = it.next();
            map.put(key, object.opt(key));
        }
        return map;
    }

    /**
     * 把一个JSONArray转化成List，其中JSONArray的子项是JSONObject
     *
     * @param array
     * @return
     */
    public static List<HashMap<String, Object>> toMapList(JSONArray array) {
        List<HashMap<String, Object>> data = new ArrayList<>();
        if (array != null && array.length() > 0) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.optJSONObject(i);
                HashMap<String, Object> map = new HashMap<>();
                Iterator<String> it = object.keys();
                while (it.hasNext()) {
                    String key = it.next();
                    map.put(key, object.opt(key));
                }
                data.add(map);
            }
        }
        return data;
    }

    public static JSONArray toJSONArray(List<HashMap<String, Object>> list) throws JSONException {
        JSONArray array = new JSONArray();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                JSONObject object = new JSONObject();
                HashMap<String, Object> map = list.get(i);
                for (String key : map.keySet()) {
                    object.put(key, map.get(key));
                }
                array.put(object);
            }
        }
        return array;
    }

    /**
     * 追加JsonArray数据
     *
     * @param mData
     * @param array
     * @return
     */
    public static JSONArray joinJSONArray(JSONArray mData, JSONArray array) {
        StringBuffer buffer = new StringBuffer();
        try {
            int len = mData.length();
            for (int i = 0; i < len; i++) {
                String obj1 = String.valueOf(mData.get(i));
                if (i == len - 1) {
                    buffer.append(obj1);
                } else {
                    buffer.append(obj1).append(",");
                }
            }
            len = array.length();
            if (len > 0) {
                buffer.append(",");
            }
            for (int i = 0; i < len; i++) {
                String obj1 = String.valueOf(array.get(i));
                if (i == len - 1) {
                    buffer.append(obj1);
                } else {
                    buffer.append(obj1).append(",");
                }
            }
            buffer.insert(0, "[").append("]");
            return new JSONArray(buffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
