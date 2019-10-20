package com.alison.generator.dao;

import com.alison.generator.model.wsUser;

public interface wsUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(wsUser record);

    int insertSelective(wsUser record);

    wsUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(wsUser record);

    int updateByPrimaryKey(wsUser record);
}