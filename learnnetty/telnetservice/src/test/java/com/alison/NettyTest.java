package com.alison;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @Author alison
 * @Date 2019/9/19  22:12
 * @Version 1.0
 * @Description
 */
@RunWith(JUnit4.class)
public class NettyTest {

    @Test
    public void test() {
        NettyTelnetServer nettyTelnetServer = new NettyTelnetServer();
        try {
            nettyTelnetServer.open();
        } catch (InterruptedException e) {
            nettyTelnetServer.close();
        }
    }
}
