package com.alison.jdbctemplateTest;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MetaFunctionInfoTest {

    @Test
    public void batchInsert() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://10.0.8.10:3306/any_data_hub");
        dataSource.setUsername("any");
        dataSource.setPassword("anywd1234");
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
        TransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus status = dataSourceTransactionManager.getTransaction(definition);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String insertSql = "INSERT INTO `any_data_hub`.`meta_function_info`(`resource_id`, `action`, `name`, `content_code`, `example`, `description`, `language`, `function_type`, `used`, `buildin`, `create_person`, `create_time`, `update_person`, `update_time`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        CsvUtilTest csvUtilTest = new CsvUtilTest();
        ArrayList<String[]> csvFileList = csvUtilTest.readCSV();
        List<Object[]> collect = csvFileList.stream().map(e -> {
            Object[] xx = e;
            return xx;
        }).collect(Collectors.toList());
        try {
            for (Object[] it : collect) {
                List<Object[]> item = Lists.newArrayList();
                item.add(it);
                log.debug("==>" + it[0]);
                jdbcTemplate.batchUpdate(insertSql, item);
            }
            dataSourceTransactionManager.commit(status);
        } catch (Throwable e) {
            e.printStackTrace();
            log.debug("-------" + e.getMessage());
            dataSourceTransactionManager.rollback(status);
        }
    }

    @Test
    public void test() {
        List<String[]> csvFileList = Lists.newArrayList();
        csvFileList.add(new String[]{"1", "2"});
        List<Object[]> collect = csvFileList.stream().map(e -> {
            Object[] xx = e;
            return xx;
        }).collect(Collectors.toList());
        collect.stream().forEach(it -> {
            System.out.println(Arrays.toString(it));
        });
    }
}
