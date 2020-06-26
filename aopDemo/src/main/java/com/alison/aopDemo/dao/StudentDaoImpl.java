package com.alison.aopDemo.dao;

import com.alison.aopDemo.Student;

/**
 * @Author alison
 * @Date 2020/6/23
 * @Version 1.0
 * @Description
 */
public class StudentDaoImpl implements StudentDao {
    public void addStudent(Student student) {
        System.out.println("增加学生...");
    }

    @Override
    public void deleteStudent() {
        System.out.println("删除学生...");
    }

    @Override
    public void removeStudent(Student stu) {
        System.out.println("移动学生...");
    }
}