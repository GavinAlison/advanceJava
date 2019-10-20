package com.alison.mybatis.test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import com.alison.mybatis.bean.Person;
import com.alison.mybatis.dao.PersonMapper;
import com.alison.mybatis.tool.MyBatisUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


public class MyBatisTest {
    /*public void testCollection()
    {
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
		PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
		Person person=personMapper.getPersonByCollection(new int[]{1,2,3,4,5});
		System.out.println(person);

	}*/

    public void getAllPersons() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
        Page<Object> page = PageHelper.startPage(1, 10);
        List<Person> persons = personMapper.getAllPersons();
        PageInfo pageInfo = new PageInfo(persons, 9);
        for (Person person : persons) {
            System.out.println(person.getId());
        }
        System.out.println(page.getPageNum());//current page
        System.out.println(page.getTotal());//total
        System.out.println(page.getPageSize());//page size
        System.out.println(pageInfo.isIsFirstPage());
        System.out.println(pageInfo.getPages());// total pages
        int[] nums = pageInfo.getNavigatepageNums();
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
        }
    }
    public static void main(String[] args) {
        new MyBatisTest().getAllPersons();
    }


}
