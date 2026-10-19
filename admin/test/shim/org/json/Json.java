package org.json;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** 交叉验证用的 JSON 解析/序列化。手写递归下降，只为让 shim 能跑。 */
final class Json {

    private final String s;
    private int i;

    private Json(String s) {
        this.s = s;
    }

    static Object parse(String text) {
        Json p = new Json(text);
        p.ws();
        Object v = p.value();
        p.ws();
        if (p.i < p.s.length()) {
            throw new IllegalArgumentException("JSON 尾部多余内容 @" + p.i);
        }
        return v;
    }

    private Object value() {
        char c = s.charAt(i);
        switch (c) {
            case '{':
                return object();
            case '[':
                return array();
            case '"':
                return string();
            case 't':
                expect("true");
                return Boolean.TRUE;
            case 'f':
                expect("false");
                return Boolean.FALSE;
            case 'n':
                expect("null");
                return JSONObject.NULL;
            default:
                return number();
        }
    }

    private Map<String, Object> object() {
        Map<String, Object> m = new LinkedHashMap<String, Object>();
        i++; // {
        ws();
        if (peek() == '}') {
            i++;
            return m;
        }
        while (true) {
            ws();
            String k = string();
            ws();
            if (s.charAt(i++) != ':') {
                throw new IllegalArgumentException("缺冒号 @" + i);
            }
            ws();
            m.put(k, value());
            ws();
            char c = s.charAt(i++);
            if (c == '}') {
                return m;
            }
            if (c != ',') {
                throw new IllegalArgumentException("对象里缺逗号 @" + i);
            }
        }
    }

    private Object array() {
        List<Object> l = new java.util.ArrayList<Object>();
        i++; // [
        ws();
        if (peek() == ']') {
            i++;
            return l;
        }
        while (true) {
            ws();
            l.add(value());
            ws();
            char c = s.charAt(i++);
            if (c == ']') {
                return l;
            }
            if (c != ',') {
                throw new IllegalArgumentException("数组里缺逗号 @" + i);
            }
        }
    }

    private String string() {
        if (s.charAt(i) != '"') {
            throw new IllegalArgumentException("期望字符串 @" + i);
        }
        i++;
        StringBuilder sb = new StringBuilder();
        while (true) {
            char c = s.charAt(i++);
            if (c == '"') {
                return sb.toString();
            }
            if (c == '\\') {
                char e = s.charAt(i++);
                switch (e) {
                    case 'n': sb.append('\n'); break;
                    case 't': sb.append('\t'); break;
                    case 'r': sb.append('\r'); break;
                    case 'b': sb.append('\b'); break;
                    case 'f': sb.append('\f'); break;
                    case '/': sb.append('/'); break;
                    case '\\': sb.append('\\'); break;
                    case '"': sb.append('"'); break;
                    case 'u':
                        sb.append((char) Integer.parseInt(s.substring(i, i + 4), 16));
                        i += 4;
                        break;
                    default:
                        throw new IllegalArgumentException("非法转义 \\" + e);
                }
            } else {
                sb.append(c);
            }
        }
    }

    private Object number() {
        int start = i;
        if (peek() == '-') {
            i++;
        }
        while (i < s.length() && "0123456789.eE+-".indexOf(s.charAt(i)) >= 0) {
            i++;
        }
        String t = s.substring(start, i);
        if (t.indexOf('.') >= 0 || t.indexOf('e') >= 0 || t.indexOf('E') >= 0) {
            return Double.valueOf(t);
        }
        return Long.valueOf(t);
    }

    private char peek() {
        return i < s.length() ? s.charAt(i) : '\0';
    }

    private void ws() {
        while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
            i++;
        }
    }

    private void expect(String t) {
        if (!s.startsWith(t, i)) {
            throw new IllegalArgumentException("期望 " + t + " @" + i);
        }
        i += t.length();
    }

    /* ------------------------------------------------------------------ */

    /** 把解析器产出的原生 Map/List 递归包成 JSONObject/JSONArray。 */
    static Object wrap(Object o) {
        if (o instanceof Map) {
            JSONObject j = new JSONObject();
            for (Map.Entry<?, ?> e : ((Map<?, ?>) o).entrySet()) {
                j.put(String.valueOf(e.getKey()), wrap(e.getValue()));
            }
            return j;
        }
        if (o instanceof List) {
            JSONArray a = new JSONArray();
            for (Object v : (List<?>) o) {
                a.put(wrap(v));
            }
            return a;
        }
        return o;
    }

    static void write(StringBuilder sb, Object o) {
        if (o == null || o == JSONObject.NULL) {
            sb.append("null");
        } else if (o instanceof String) {
            quote(sb, (String) o);
        } else if (o instanceof Boolean || o instanceof Number) {
            sb.append(o.toString());
        } else if (o instanceof Map) {
            sb.append('{');
            boolean first = true;
            for (Map.Entry<?, ?> e : ((Map<?, ?>) o).entrySet()) {
                if (!first) {
                    sb.append(',');
                }
                first = false;
                quote(sb, String.valueOf(e.getKey()));
                sb.append(':');
                write(sb, e.getValue());
            }
            sb.append('}');
        } else if (o instanceof List) {
            sb.append('[');
            boolean first = true;
            for (Object v : (List<?>) o) {
                if (!first) {
                    sb.append(',');
                }
                first = false;
                write(sb, v);
            }
            sb.append(']');
        } else if (o instanceof JSONObject) {
            write(sb, ((JSONObject) o).raw());
        } else if (o instanceof JSONArray) {
            write(sb, ((JSONArray) o).raw());
        } else {
            quote(sb, String.valueOf(o));
        }
    }

    private static void quote(StringBuilder sb, String s) {
        sb.append('"');
        for (int k = 0; k < s.length(); k++) {
            char c = s.charAt(k);
            switch (c) {
                case '"': sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\n': sb.append("\\n"); break;
                case '\r': sb.append("\\r"); break;
                case '\t': sb.append("\\t"); break;
                default:
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('"');
    }
}
