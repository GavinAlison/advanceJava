package com.alison.file;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @Author alison
 * @Date 2019/10/13  17:21
 * @Version 1.0
 * @Description
 */
public class FileMmapCopy {
    // 使用内存映射文件的方式实现文件复制的功能(直接操作缓冲区), 这块内存叫堆外内存，只用于映射文件的内存
    public static void main(String[] args) throws IOException {
        FileChannel inchannel = FileChannel.open(Paths.get("E:","/memoryMap/专利号采集.doc"),
                StandardOpenOption.READ);
        FileChannel outchannel = FileChannel.open(Paths.get("E:","/destination/1.doc"),
                StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.READ);
        // 内存映射文件
        MappedByteBuffer inMapperBuf = inchannel.map(FileChannel.MapMode.READ_ONLY, 0, inchannel.size());
        MappedByteBuffer outMapperBuf = outchannel.map(FileChannel.MapMode.READ_WRITE, 0, inchannel.size());
        // 对缓存区进行数据读写操作
        byte[] dstination = new byte[inMapperBuf.remaining()];
        inMapperBuf.get(dstination);
        outMapperBuf.put(dstination);

        outchannel.close();
        inchannel.close();
    }
}
