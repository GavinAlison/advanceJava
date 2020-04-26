package com.alison.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/****
 * 序列化工具
 */
public class SerializeUtil {

    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // 序列化
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> Object deserialize(byte[] bytes, Class<T> className) {
        T tmpObject = null;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            // 反序列化
            tmpObject = (T) ois.readObject();
            return tmpObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}