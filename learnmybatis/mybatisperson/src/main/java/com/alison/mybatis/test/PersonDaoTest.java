package com.alison.mybatis.test;

import com.alison.mybatis.bean.Person;
import com.alison.mybatis.bean.User;
import com.alison.mybatis.dao.PersonDao;
import com.alison.mybatis.dao.PersonDaoImpl;
import com.alison.mybatis.tool.MyBatisUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class PersonDaoTest {
    //    添加用户
    private void add(Person person) throws Exception {
        //得到连接对象
        // 默认开启事物的
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        int result = sqlSession.insert("com.alison.mybatis.dao.PersonMapper.addPerson", person);
        sqlSession.commit();
        System.out.println(result);
    }

    //根据用户 id（主键）查询用户信息, 传递一个参数
    private void findPersonById() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        Person p = sqlSession.selectOne("com.alison.mybatis.dao.PersonMapper.findPersonById", 1);
        MyBatisUtil.closeSqlSession();
        System.out.println(p);
    }

    //    根据用户名称模糊查询用户信息列表
    private void findPersonByUsername() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        List<Person> list = sqlSession.selectList("com.alison.mybatis.dao.PersonMapper.findPersonByUsername", "to");
        for (Person p : list) {
            System.out.println(p);
        }
        MyBatisUtil.closeSqlSession();
    }

    //    删除用户
    private void delete() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        int result = sqlSession.delete("com.alison.mybatis.dao.PersonMapper.deletePerson", 10008);
        System.out.println(result);
        sqlSession.commit();
        MyBatisUtil.closeSqlSession();
    }

    //    更新用户信息
    private void update() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        int result = sqlSession.update("com.alison.mybatis.dao.PersonMapper.updatePerson", 9);
        System.out.println(result);
        sqlSession.commit();
        MyBatisUtil.closeSqlSession();
    }


    public static void main(String[] args) throws Exception {
        PersonDaoTest demo = new PersonDaoTest();
        Person person = new Person("zhangsan2", "12312@qq.com", "10000");
//        demo.add(person);
//        demo.delete();
//        demo.findUserById();
//        demo.findUserByUsername();
//        demo.update();


    }
}
