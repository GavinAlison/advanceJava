package com.alison;

/**
 * @Author alison
 * @Date 2019/11/17  15:16
 * @Version 1.0
 * @Description
 */
public class SampleServiceImpl implements SampleService {
    public int add(int x, int y) {
        return x + y;
    }

//    @AuthCheck
    public String getPassword(String username) {
        return "password";
    }
}
