package com.alison.mybatis.tool;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;

public class MyBatisUtil {
    private static ThreadLocal<SqlSession> threadlocal = new ThreadLocal<SqlSession>();
    private static SqlSessionFactory sqlSessionFactory;
    private static String resource = "mybatis-config.xml";

    /**
     * 加载配置文件
     */
    static {
        try {
            // 方式一
            Reader reader = Resources.getResourceAsReader(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            // 方式二
//            InputStream inputStream = Resources.getResourceAsStream(resource);
//            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 禁止外界通过new方法创建
     */
    private MyBatisUtil() {
    }

    /**
     * 获取SqlSession
     */
    public static SqlSession getSqlSession() {
        //从当前线程中获取SqlSession对象
        SqlSession sqlSession = threadlocal.get();
        //如果SqlSession对象为空
        if (sqlSession == null) {
            //在SqlSessionFactory非空的情况下，获取SqlSession对象
            sqlSession = sqlSessionFactory.openSession();
            threadlocal.set(sqlSession);
        }
        //返回SqlSession对象
        return sqlSession;
    }

    /**
     * 关闭SqlSession与当前线程分开
     */
    public static void closeSqlSession() {
        //从当前线程中获取SqlSession对象
        SqlSession sqlSession = threadlocal.get();
        //如果SqlSession对象非空
        if (sqlSession != null) {
            //关闭SqlSession对象
            sqlSession.close();
            //分开当前线程与SqlSession对象的关系，目的是让GC尽早回收
            threadlocal.remove();
        }
    }


    public static void main(String[] args) {
        Connection conn = MyBatisUtil.getSqlSession().getConnection();
        System.out.println(conn != null ? "连接成功" : "否");
    }

    public static SqlSession getPrototypeSqlSession() {
        return sqlSessionFactory.openSession();
    }

}
