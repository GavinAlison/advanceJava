package com.alison.nio.case7_fileChannel;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class FileChannelDemo {

    public static void open() {
        //    打开 FileChannel
        String fromPath = FileChannelDemo.class.getResource("/fromFile.txt").getPath();
        String toPath = FileChannelDemo.class.getResource("/toFile.txt").getPath();
        try (FileInputStream inputStream = new FileInputStream(fromPath)) {
            FileChannel fileChannel = inputStream.getChannel();
            FileOutputStream outputStream = new FileOutputStream(toPath);
            FileChannel outChannel = outputStream.getChannel();
//            从 FileChannel 读取数据
            ByteBuffer byteBuffer = ByteBuffer.allocate(48);
            log.info("" + fileChannel.read(byteBuffer));
            while (fileChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
//                向 FileChannel 写数据
                while (byteBuffer.hasRemaining()) {
                    outChannel.write(byteBuffer);
                }
                byteBuffer.clear();
            }
            // 关闭 FileChannel
            // channel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void write2FileForChannel() {
        // 变化的是class下的文件
        FileOutputStream fileOutputStream;
        FileChannel fileChannel;
        ByteBuffer mBuf;
        try {
            String filePath = FileChannelDemo.class.getResource("/test.txt").getPath();
            fileOutputStream = new FileOutputStream(filePath);
            fileChannel = fileOutputStream.getChannel();
            mBuf = ByteBuffer.allocateDirect(26);
            for (int i = 0; i < 26; i++)
                mBuf.put((byte) ('A' + i));
            mBuf.rewind();
            fileChannel.write(mBuf);
            fileChannel.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    private static void writeData2Channel() {
        try {
            String filePath = FileChannelDemo.class.getResource("/channel.txt").getPath();
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            FileChannel fileChannel = fileOutputStream.getChannel();
            String newData = "New String: " + System.nanoTime();
            log.info("newData: " + newData);
            ByteBuffer byteBuffer = ByteBuffer.allocate(48);
            byteBuffer.clear();
            byteBuffer.put(newData.getBytes());
            byteBuffer.flip();
//            FileChannel Size
//            The size() method of the FileChannel object returns the file size of the file the channel is connected to. Here is a simple example:
            log.info("file size: " + fileChannel.size());
//            FileChannel Position
//            You can obtain the current position of the FileChannel object by calling the position() method.
//            fileChannel.position(4);
//            FileChannel Truncate
            fileChannel.truncate(4);
//            FileChannel Force   ---->flush cache to disk
//            fileChannel.force(true);
            while (byteBuffer.hasRemaining()) {
                fileChannel.write(byteBuffer);
            }
            fileChannel.force(true);
            FileInputStream fileInputStream = new FileInputStream(filePath);
            FileChannel intputChannel = fileInputStream.getChannel();
            log.info("file size: " + intputChannel.size());

            intputChannel.position(4);
            log.info("inputchannel position: " + intputChannel.position());
            ByteBuffer byteBufferOffset = ByteBuffer.allocate(48);

            StringBuilder sb = new StringBuilder();
            while (intputChannel.read(byteBufferOffset) != -1) {
                byteBufferOffset.flip();
                while (byteBufferOffset.hasRemaining()) {
                    sb.append((char) byteBufferOffset.get());
                }
                log.info("offset: 4, content: " + sb.toString());
                byteBufferOffset.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        open();
//        write2FileForChannel();
        writeData2Channel();

    }
}
