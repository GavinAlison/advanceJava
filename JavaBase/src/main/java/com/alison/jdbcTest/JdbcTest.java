package com.alison.jdbcTest;

import lombok.Getter;
import lombok.Setter;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.*;

public class JdbcTest {

    // 最原始的JDBC操作
    @Test
    public void originJdbc() throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 创立连接
            conn = DriverManager.getConnection("jdbc:mysql:///message", "root", "");
            String ssql = "select * from message where id > ?";
            //预编译sql语句
            pst = conn.prepareStatement(ssql);
            //设置参数值
            pst.setLong(1, 1);
            //执行sql语句 --> 返回一个结果集
            rs = pst.executeQuery();
            //遍历结果集
            while (rs.next()) {
                int id = rs.getInt("id");
                String command = rs.getString("command");
                String CONTENT = rs.getString("CONTENT");
                String DESCRIPTION = rs.getString("DESCRIPTION");
//                message m = new message(id, command, CONTENT, DESCRIPTION);
                System.out.println("");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // 关闭
            rs.close();
            pst.close();
            conn.close();
        }
    }

    // jdbcTemplate 的查询
//    1.查询结果集是一个对象
//    a.查询返回结果对象 queryForObject();
    @Test
    public void SelectObject() {
// 1.设置数据库信息
        DriverManagerDataSource source = new DriverManagerDataSource();
        source.setDriverClassName("com.mysql.jdbc.Driver");
        source.setUrl("jdbc:mysql:///message");
        source.setUsername("root");
        source.setPassword("");
// 2.建立jdbcTemplate对象，设置数据源
        JdbcTemplate jdbcTemplate = new JdbcTemplate(source);
//3.调用修改模板
        String sql = "select * from message where id = ?";
        /*
         * 1.sql sql语句
         *  2. 对结构集中的数据进行封装
         *  3.可变参数，对应sql语句种的“？”
         * */
//        message m = jdbcTemplate.queryForObject(sql, new MyRowMapper(), 1);
//        System.out.println(m);

    }

//    b.数据封装类MyRowMapper.java

//    class MyRowMapper implements RowMapper<message> {
//        //这个只封装到返回结果集，但对结构集没有进行在封装
//        @Override
//        public message mapRow(ResultSet rs, int num) throws SQLException {
////1.从结果集中的数据得到
//            int id = rs.getInt("id");
//            String command = rs.getString("command");
//            String CONTENT = rs.getString("CONTENT");
//            String DESCRIPTION = rs.getString("DESCRIPTION");
////2.数据的封装
//            message m = new message(id, command, CONTENT, DESCRIPTION);
//            return m;
//
//        }
    }



    @Getter
    @Setter
    class Message {
        private int id;
        private String command;
        private String content;
        private String description;
    }