package org.json;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 交叉验证用的极简 org.json 替身，只覆盖被验证代码实际用到的 API。
 * 不是通用 JSON 库，也不进入 APK。
 */
public class JSONObject {

    public static final Object NULL = new Object();

    private final LinkedHashMap<String, Object> m = new LinkedHashMap<String, Object>();

    public JSONObject() {
    }

    public JSONObject(String text) {
        Object o = Json.parse(text);
        if (!(o instanceof Map)) {
            throw new IllegalArgumentException("根节点不是对象");
        }
        // 解析器产出的是原生 Map/List，这里递归包成 JSONObject/JSONArray，
        // 否则 optJSONArray() 会认不出来（这就是"解开了但字段是空的"那种假成功）
        Object wrapped = Json.wrap(o);
        m.putAll(((JSONObject) wrapped).m);
    }

    public JSONObject put(String k, Object v) {
        m.put(k, v);
        return this;
    }

    public JSONObject put(String k, int v) {
        m.put(k, v);
        return this;
    }

    public JSONObject put(String k, long v) {
        m.put(k, v);
        return this;
    }

    public JSONObject put(String k, boolean v) {
        m.put(k, v);
        return this;
    }

    public boolean has(String k) {
        return m.containsKey(k) && m.get(k) != null && m.get(k) != NULL;
    }

    public boolean isNull(String k) {
        return !m.containsKey(k) || m.get(k) == null || m.get(k) == NULL;
    }

    public Object get(String k) {
        Object v = m.get(k);
        if (v == null) {
            throw new IllegalArgumentException("no value for " + k);
        }
        return v;
    }

    public String getString(String k) {
        Object v = get(k);
        if (v instanceof String) {
            return (String) v;
        }
        throw new IllegalArgumentException(k + " 不是字符串");
    }

    public int getInt(String k) {
        return ((Number) get(k)).intValue();
    }

    public long getLong(String k) {
        return ((Number) get(k)).longValue();
    }

    public String optString(String k) {
        return optString(k, "");
    }

    public String optString(String k, String dflt) {
        Object v = m.get(k);
        return v == null || v == NULL ? dflt : String.valueOf(v);
    }

    public int optInt(String k, int dflt) {
        Object v = m.get(k);
        return v instanceof Number ? ((Number) v).intValue() : dflt;
    }

    public long optLong(String k, long dflt) {
        Object v = m.get(k);
        return v instanceof Number ? ((Number) v).longValue() : dflt;
    }

    public boolean optBoolean(String k, boolean dflt) {
        Object v = m.get(k);
        return v instanceof Boolean ? (Boolean) v : dflt;
    }

    public JSONObject optJSONObject(String k) {
        Object v = m.get(k);
        return v instanceof JSONObject ? (JSONObject) v : null;
    }

    public JSONArray optJSONArray(String k) {
        Object v = m.get(k);
        return v instanceof JSONArray ? (JSONArray) v : null;
    }

    public JSONArray getJSONArray(String k) {
        Object v = get(k);
        if (!(v instanceof JSONArray)) {
            throw new IllegalArgumentException(k + " 不是数组");
        }
        return (JSONArray) v;
    }

    public int length() {
        return m.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Json.write(sb, m);
        return sb.toString();
    }

    /* 供 Json 解析器使用 */
    Map<String, Object> raw() {
        return m;
    }

    static List<Object> newList() {
        return new ArrayList<Object>();
    }
}
