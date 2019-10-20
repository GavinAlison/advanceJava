package com.alison.mybatis.dao;

import com.alison.mybatis.bean.Person;

public interface PersonDao {
    //根据id查询用户信息
    public Person findPersonById(int id) throws Exception;

    //添加用户信息
    public void addPerson(Person person) throws Exception;

    //删除用户信息
    public void deletePerson(int id) throws Exception;

    // 更改
    public void update(int id);
}
