package com.jvm;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class DumpDemo {
    private static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static long addressOf(Object o) throws Exception {
        Object[] array = new Object[] { o };
        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        int addressSize = unsafe.addressSize();
        long objectAddress;
        switch (addressSize) {
            case 4:
                objectAddress = unsafe.getInt(array, baseOffset);
                break;
            case 8:
                objectAddress = unsafe.getLong(array, baseOffset);
                break;
            default:
                throw new Error("unsupported address size: " + addressSize);
        }
        return (objectAddress);
    }

    public static void main(String... args) throws Exception {
        Object myObj = new Demo1();
        long address = addressOf(myObj);
        System.out.printf("Addess=0x%x, HashCode=0x%x\n", address, System.identityHashCode(myObj));

        // print fields offset
        long offset = 0;
        Field[] fields = myObj.getClass().getDeclaredFields();
        for (Field field : fields) {
            offset = offset = unsafe.fieldOffset(field);
            System.out.println(field.getName() + ": offSet=" + offset);
        }

        printBuffer(address, 16);
    }

    public static void printBuffer(long address, long size) {
        for (long i = 0; i < size; i++) {
            byte b = unsafe.getByte(address + i);
            System.out.printf("[%d]=0x%x\n", i, b);
        }
        System.out.println();
    }

}

class Demo1 {
    byte a = 1;
    byte b = 2;
    byte c = 3;
}