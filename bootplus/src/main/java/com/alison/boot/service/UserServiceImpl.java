package com.alison.boot.service;

import com.alison.boot.dao.UserDao;
import com.alison.boot.vo.UserDo;
import com.alison.boot.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public void modifyUser(UserVO userVo) {
        UserDo userDo = new UserDo();
        BeanUtils.copyProperties(userVo, userDo);
        userDao.modify(userDo);
    }
}
