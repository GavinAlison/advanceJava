package com.alison.Zerocopy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author alison
 * @Date 2019/10/13  22:21
 * @Version 1.0
 * @Description
 */
@Slf4j
public class ZerocopyClient {
    public static void main(String[] args) throws IOException {
        ZerocopyClient sfc = new ZerocopyClient();
        sfc.testSendfile();
    }

    public void testSendfile() throws IOException {
        String host = "localhost";
        int port = 9026;
        SocketAddress sad = new InetSocketAddress(host, port);
        SocketChannel sc = SocketChannel.open();
        sc.connect(sad);
        sc.configureBlocking(true);

        String fname = "E:\\destination\\1.doc";
        FileChannel fc = new FileInputStream(fname).getChannel();
        long start = System.nanoTime();
        long nsent = 0, curnset = 0;
        curnset = fc.transferTo(0, fc.size(), sc);
        log.info("发送的总字节数:" + curnset + " 耗时(ns):" + (System.nanoTime() - start));
        try {
            sc.close();
            fc.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
