package com.example.administrator.myonetext.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/5.
 */

public class ConvertUtils {
    public static String toString(InputStream is, String charset) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset));
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                } else {
                    sb.append(line).append("\n");
                }
            }
            reader.close();
            is.close();
        } catch (IOException e) {
        }
        return sb.toString();
    }

    public static String toString(InputStream is) {
        return toString(is, "utf-8");
    }


    /**
     * 将list转为json
     * @param lists
     * @param sb
     * @return
     */
    public static StringBuilder listToJson(List<?> lists, StringBuilder sb) {
        if (sb == null) sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < lists.size(); i++) {
            Object o = lists.get(i);
            if (o instanceof Map<?, ?>) {
                Map<?, ?> map = (Map<?, ?>) o;
                mapToJson(map, sb);
            } else if (o instanceof List<?>) {
                List<?> l = (List<?>) o;
                listToJson(l, sb);
            } else {
                sb.append("\"" + o + "\"");
            }
            if (i != lists.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb;
    }

    /**
     * 将map转为json
     * @param map
     * @param sb
     * @return
     */
    public static StringBuilder mapToJson(Map<?, ?> map, StringBuilder sb) {
        if (sb == null) sb = new StringBuilder();
        sb.append("{");
        Iterator<?> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iter.next();
            String key = entry.getKey() != null ? entry.getKey().toString() : "";
            sb.append("\"" + stringToJson(key) + "\":");
            Object o = entry.getValue();
            if (o instanceof List<?>) {
                List<?> l = (List<?>) o;
                listToJson(l, sb);
            } else if (o instanceof Map<?, ?>) {
                Map<?, ?> m = (Map<?, ?>) o;
                mapToJson(m, sb);
            } else {
                String val = entry.getValue() != null ? entry.getValue().toString() : "";
                sb.append("\"" + stringToJson(val) + "\"");
            }
            if (iter.hasNext()) sb.append(",");
        }
        sb.append("}");
        return sb;
    }

    /**
     * 将字符串转为json数据
     * @param str 数据字符串
     * @return json字符串
     */
    private static String stringToJson(String str) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 根据现在系统时间生成图片名
     *
     * @return
     */
    public static String generateFileName() {
        // 创建一个以当前时间为名称的文件
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return "IMG" + dateFormat.format(date) + ".jpg";
    }
}
