package com.alison.mybatis.dao;

import com.alison.mybatis.bean.Person;
import com.alison.mybatis.tool.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class PersonDaoImpl implements PersonDao {
    //需要在 Dao 实现类中注入 SqlsessionFactory
    //这里通过构造方法注入
    private SqlSessionFactory sqlSessionFactory;

    public PersonDaoImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public Person findPersonById(int id) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Person person = sqlSession.selectOne("com.alison.mybatis.dao.PersonMapper.findPersonById", 1);
        sqlSession.close();
        return person;
    }

    public void addPerson(Person person) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int result = sqlSession.insert("com.alison.mybatis.dao.PersonMapper.addPerson", person);
        sqlSession.commit();
        sqlSession.close();
    }

    public void deletePerson(int id) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int result = sqlSession.delete("com.alison.mybatis.dao.PersonMapper.deletePerson", 10008);
        sqlSession.commit();
        sqlSession.close();
    }

    public void update(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int result = sqlSession.update("com.alison.mybatis.dao.PersonMapper.updatePerson", 10008);
        sqlSession.commit();
        sqlSession.close();
    }
}
