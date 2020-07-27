package com.alison.Utils;

import java.io.*;
import java.util.Optional;

/**
 * Byte数组工具类
 */
public class ByteArrayUtils {

    public static <T> Optional<byte[]> objectToBytes(T obj) {
        byte[] bytes = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ObjectOutputStream sOut = new ObjectOutputStream(out)) {
            sOut.writeObject(obj);
            sOut.flush();
            bytes = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(bytes);
    }

    public static <T> Optional<T> bytesToObject(byte[] bytes) {
        T t = null;
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try (ObjectInputStream sIn = new ObjectInputStream(in)) {
            t = (T) sIn.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(t);

    }
}