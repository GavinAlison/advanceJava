package com.alison.Zerocopy;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author alison
 * @Date 2019/10/13  22:16
 * @Version 1.0
 * @Description disk-nic零拷贝
 * @link https://www.cnblogs.com/z-sm/p/6547709.html
 */
@Slf4j
public class ZerocopyServer {

    ServerSocketChannel listener = null;

    final String fileName = "D:\\水瓶测试文件\\testdata\\1.xlsx";

    protected void myStartup() {
        InetSocketAddress listenAddr = new InetSocketAddress(9026);

        try {
            listener = ServerSocketChannel.open();
            ServerSocket ss = listener.socket();
            ss.setReuseAddress(true);
            ss.bind(listenAddr);
            log.info("监听的端口:" + listenAddr.toString());
        } catch (IOException e) {
            log.error("端口绑定失败 : " + listenAddr.toString() + " 端口可能已经被使用,出错原因: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        ZerocopyServer dns = new ZerocopyServer();
        dns.myStartup();
        dns.readData();
    }

    private void readData() {
        ByteBuffer dst = ByteBuffer.allocate(4096);
        FileChannel outChannel = null;
        FileOutputStream outputStream = null;
        File destFile = new File(fileName);
        try {
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            outputStream = new FileOutputStream(destFile);
            // 需要append, 追加模式
//                outChannel = FileChannel.open(Paths.get(fileName),
//                        StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            outChannel = outputStream.getChannel();
            SocketChannel conn = listener.accept();
            log.info("创建的连接: " + conn);
            conn.configureBlocking(true);
            int nread = 0;
            while (nread != -1) {
                try {
                    // 每次读取的数值长度
                    nread = conn.read(dst);
//                        log.info(""+nread);
                    dst.flip();
                    outChannel.write(dst);
                } catch (IOException e) {
                    e.printStackTrace();
                    nread = -1;
                }
                dst.clear();
            }
            log.info("over");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outChannel != null) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
