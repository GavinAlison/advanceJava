package com.alison.aopDemo.service;

import com.alison.aopDemo.Student;
import com.alison.aopDemo.dao.StudentDao;

/**
 * @Author alison
 * @Date 2020/6/23
 * @Version 1.0
 * @Description
 */
public class StudentServiceImpl implements StudentService{

    StudentDao stuDao;

    public void setStuDao(StudentDao stuDao) {
        this.stuDao = stuDao;
    }

    @Override
    public void removeStudent(Student stu) {
        //stuDao = null;  // 进行异常通知的测试代码。空指针异常。
        stuDao.removeStudent(stu);
    }
}