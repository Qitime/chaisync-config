package org.json;

import java.util.ArrayList;
import java.util.List;

/** 交叉验证用的极简 JSONArray。只覆盖被验证代码用到的 API。 */
public class JSONArray {

    private final List<Object> list = new ArrayList<Object>();

    public JSONArray() {
    }

    public JSONArray put(Object v) {
        list.add(v);
        return this;
    }

    public int length() {
        return list.size();
    }

    public Object get(int i) {
        return list.get(i);
    }

    public JSONObject optJSONObject(int i) {
        Object v = i >= 0 && i < list.size() ? list.get(i) : null;
        return v instanceof JSONObject ? (JSONObject) v : null;
    }

    public JSONObject getJSONObject(int i) {
        Object v = list.get(i);
        if (!(v instanceof JSONObject)) {
            throw new IllegalArgumentException("第 " + i + " 项不是对象");
        }
        return (JSONObject) v;
    }

    public String optString(int i, String dflt) {
        Object v = i >= 0 && i < list.size() ? list.get(i) : null;
        return v == null || v == JSONObject.NULL ? dflt : String.valueOf(v);
    }

    List<Object> raw() {
        return list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Json.write(sb, list);
        return sb.toString();
    }
}
