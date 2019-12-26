package com.alison;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.sql.*;

/**
 * @Author alison
 * @Date 2019/12/23  15:09
 * @Version 1.0
 * @Description 测试代码： 使用连接池用时500ms, 不使用连接池用时8s, 相差16倍.
 */
public class HikariTest {
    private static final HikariDataSource ds;

    static {
        HikariConfig conf = new HikariConfig();
        conf.setUsername("root");
        conf.setPassword("root");
        conf.setJdbcUrl("jdbc:mysql://localhost:3306/abc");
        ds = new HikariDataSource(conf);
    }


    @Test
    public void testArr(){
//         this.elementData = (T[]) Array.newInstance(clazz, 32);
    }
    @Test
    public void test_1() throws SQLException {
        long st = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select now() from dual");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
            connection.close();
        }
        System.out.println(System.currentTimeMillis() - st);
    }

    @Test
    public void test_2() throws SQLException {
        long st = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abc", "root", "root");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select now() from dual");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
            connection.close();
        }
        System.out.println(System.currentTimeMillis() - st);
    }
}
