package com.example.wx.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by yao on 2017/5/25.
 */
public class StringUtil {
    private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

    public static String getStrValue(Object object) {
        if (object == null || "".equals(object.toString().trim())) {
            return null;
        } else {
            return object.toString();
        }
    }

    public static Long getLongValue(Object object) {
        if (object == null || "".equals(object.toString().trim())) {
            return null;
        } else {
            return Long.valueOf(object.toString());
        }
    }

    public static Integer getIntValue(Object object) {
        if (object == null || "".equals(object.toString().trim())) {
            return null;
        } else {
            return Integer.valueOf(object.toString());
        }
    }

    public static String sha1(String encryptStr) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.update(encryptStr.getBytes("UTF-8"));
        byte[] digest = md.digest();
        StringBuffer sha1 = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            sha1.append(Character.forDigit((digest[i] & 0xF0) >> 4, 16));
            sha1.append(Character.forDigit((digest[i] & 0xF), 16));
        }

        encryptStr = sha1.toString();
        logger.debug("加密结果-------------" + encryptStr);
        return encryptStr;
    }

    //sha1加密直接使用appach的方法
    public static String getSha1(String str) {
        return DigestUtils.shaHex(str);
    }

    public static String getSortStr(Map<String, Object> map) {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != "") {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String a = sb.toString();
        a = a.substring(0, a.length() - 1);
        logger.debug("生成-------------" + a);
        return a;
    }
}
