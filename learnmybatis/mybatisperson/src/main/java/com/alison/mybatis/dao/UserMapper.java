package com.alison.mybatis.dao;

import com.alison.mybatis.bean.User;
import com.alison.mybatis.vo.UserCustom;
import com.alison.mybatis.vo.UserQueryVo;

import java.util.List;

public interface UserMapper {
    // 用户信息综合查询
    public List<UserCustom> findUserList(UserQueryVo userQueryVo);

    // 用户信息综合查询总数
    public int findUserCount(UserQueryVo userQueryVo);

    public User findUserById(int id);

    public UserCustom findUserListHe(UserQueryVo userQueryVo);

    public void updateUserById(User user);

}
