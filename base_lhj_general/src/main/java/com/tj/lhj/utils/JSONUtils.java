package com.tj.lhj.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

public class JSONUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JSONUtils() {
    }

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    public static String beanToJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static <T> T jsonToBean(String jsonStr, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonStr, clazz);
        } catch (JsonParseException var3) {
            var3.printStackTrace();
        } catch (JsonMappingException var4) {
            var4.printStackTrace();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        return null;
    }

    public static <T> Map<String, Object> jsonToMap(String jsonStr) throws Exception {
        return (Map)objectMapper.readValue(jsonStr, Map.class);
    }

    public static <T> Map<String, T> jsonToMap(String jsonStr, Class<T> clazz) throws Exception {
        Map<String, Map<String, Object>> map = (Map)objectMapper.readValue(jsonStr, new TypeReference<Map<String, T>>() {
        });
        Map<String, T> result = new HashMap();
        Iterator var4 = map.entrySet().iterator();

        while(var4.hasNext()) {
            Map.Entry<String, Map<String, Object>> entry = (Map.Entry)var4.next();
            result.put(entry.getKey(), mapToBean((Map)entry.getValue(), clazz));
        }

        return result;
    }

    public static <T> List<T> jsonToList(String jsonArrayStr, Class<T> clazz) throws Exception {
        List<Map<String, Object>> list = (List)objectMapper.readValue(jsonArrayStr, new TypeReference<List<T>>() {
        });
        List<T> result = new ArrayList();
        Iterator var4 = list.iterator();

        while(var4.hasNext()) {
            Map<String, Object> map = (Map)var4.next();
            result.add(mapToBean(map, clazz));
        }

        return result;
    }

    public static <T> T mapToBean(Map map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }
}
