package com.jvm;

import java.util.ArrayList;
import java.util.List;

public class OOMTest {
    public void stackOverFlowMethod() {
        stackOverFlowMethod();
    }

    public static void main(String[] args) {
        OOMTest oom = new OOMTest();
//        oom.stackOverFlowMethod();
        oom.outmemory1();

    }
    // -Xmn:
// java -verbose:gc -Xmn10M -Xms20M -Xmx20M -XX:+PrintGC OOMTest
    public void outmemory(){
        List<byte[]> buffer = new ArrayList<byte[]>();
        buffer.add(new byte[10*1024*1024]);
    }
    public void outmemory1(){
        List buffer = new ArrayList<>();
        for(int i=0;i<  20_0000_0000; i++){
            buffer.add(1);
        }
    }
}
