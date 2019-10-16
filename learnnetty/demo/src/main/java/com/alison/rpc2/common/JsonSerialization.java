package com.alison.rpc2.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonSerialization implements Serializer {

    private ObjectMapper objectMapper;

    public JsonSerialization() {
        this.objectMapper = new ObjectMapper();
    }


    @Override
    public <T> byte[] serialize(T obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clz) {
        try {
            return objectMapper.readValue(data, clz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}