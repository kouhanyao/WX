package com.example.wx.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * Created by 寇含尧 on 2017/11/12.
 */
public class JsonUtil {
    /**
     * json转map
     * @param str
     * @return
     */
    public static Map<String, Object> JsonStr2Map(String str) {
        Map<String, Object> result = JSON.parseObject(str, new TypeReference<Map<String, Object>>() {
        });
        return result;
    }
}
