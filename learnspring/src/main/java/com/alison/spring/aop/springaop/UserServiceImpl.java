package com.alison.spring.aop.springaop;

import org.springframework.stereotype.Service;

/**
 * @Author alison
 * @Date 2019/11/15  9:20
 * @Version 1.0
 * @Description
 */
@Service
public class UserServiceImpl implements UserService {

    public void save() {
        System.out.println("save User");
    }

    public void update() {
        System.out.println("update User");
    }

    public void delete() {
        System.out.println("delete User");
    }
}
