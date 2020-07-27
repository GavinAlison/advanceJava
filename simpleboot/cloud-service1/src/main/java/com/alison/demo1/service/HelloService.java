package com.alison.demo1.service;

import com.alison.demo1.domain.po.Hello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

public interface HelloService {

//    String sayHello();

    Long getCounterFromMysql();

    Long getCounterFromRedis();
}


