package com.alison.aopDemo.dao;

import com.alison.aopDemo.Student;

/**
 * @Author alison
 * @Date 2020/6/23
 * @Version 1.0
 * @Description
 */
public interface StudentDao {
    void removeStudent(Student stu);

    void deleteStudent();
}
