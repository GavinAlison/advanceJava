package com.alison.test;

import com.alison.aopDemo.Student;
import com.alison.aopDemo.service.StudentService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author alison
 * @Date 2020/6/23
 * @Version 1.0
 * @Description
 */
public class AdviceTest {

    @Test
    public void beforeAdvice() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        StudentService stuService = (StudentService) context.getBean("studentService");
        Student stu = new Student();
        stuService.removeStudent(stu);
    }
}
