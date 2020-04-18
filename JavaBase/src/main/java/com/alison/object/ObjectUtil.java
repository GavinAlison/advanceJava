package com.alison.object;

import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author alison
 * @Date 2020/4/18
 * @Version 1.0
 * @Description
 */
@UtilityClass
public class ObjectUtil {

    public Object getOrDefault(Object s) {
        if (s instanceof String) {
            if (s == null || ((String) s).length() == 0) {
                return "";
            }
        } else if (s instanceof Integer) {
            if (s == null || ((Integer) s).intValue() == 0) {
                return 0;
            }
        } else if (s instanceof Collections) {
            if (s instanceof List) {
                if (s == null || ((List) s).isEmpty()) {
                    return Collections.emptyList();
                }
            }
            if (s instanceof Set) {
                if (s == null || ((Set) s).isEmpty()) {
                    return Collections.emptySet();
                }
            }
        } else if (s instanceof Map) {
            if (s == null || ((Map) s).isEmpty()) {
                return Collections.emptyMap();
            }
        }
        if (s == null) {
            return "";
        }
        return s;
    }
    public Object getOrDefault(Object s, Object defaultObj) {
        if (s instanceof String) {
            if (s == null || ((String) s).length() == 0) {
                return defaultObj;
            }
        } else if (s instanceof Integer) {
            if (s == null || ((Integer) s).intValue() == 0) {
                return defaultObj;
            }
        } else if (s instanceof Collections) {
            if (s instanceof List) {
                if (s == null || ((List) s).isEmpty()) {
                    return defaultObj;
                }
            }
            if (s instanceof Set) {
                if (s == null || ((Set) s).isEmpty()) {
                    return defaultObj;
                }
            }
        } else if (s instanceof Map) {
            if (s == null || ((Map) s).isEmpty()) {
                return defaultObj;
            }
        }
        if (s == null) {
            return defaultObj;
        }
        return s;
    }
}
