package com.alison.spring.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

public class ObjectTransform {

    public ObjectTransform() {
    }

    public static String bean2Json(Object object) {
        if (object == null) {
            return "";
        } else {
            try {
                return (new ObjectMapper()).writeValueAsString(object);
            } catch (JsonProcessingException var2) {
                return "";
            }
        }
    }

    public static Object json2Bean(String json, Class objectType) {
        if (StringUtils.isEmpty(json)) {
            return null;
        } else {
            try {
                return (new ObjectMapper()).readValue(json, objectType);
            } catch (Exception var3) {
                return null;
            }
        }
    }
}
