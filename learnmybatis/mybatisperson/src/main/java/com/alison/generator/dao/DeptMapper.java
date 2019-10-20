package com.alison.generator.dao;

import com.alison.generator.model.Dept;

public interface DeptMapper {
    int insert(Dept record);

    int insertSelective(Dept record);
}