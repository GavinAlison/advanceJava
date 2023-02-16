package com.alison.boot.t2;

import com.alison.boot.dao.UserDao;
import com.alison.boot.service.UserService;
import com.alison.boot.vo.UserDo;
import com.alison.boot.vo.UserVO;
import org.apache.catalina.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(PowerMockRunner.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser() {
        UserVO userVO = new UserVO();
        userVO.setId(1L);
        userVO.setName("tom");
        userVO.setDesc("test user");
        userService.modifyUser(userVO);
        ArgumentCaptor<UserDo> argumentCaptor = ArgumentCaptor.forClass(UserDo.class);
        Mockito.verify(userDao).modify(argumentCaptor.capture());
        UserDo userDo = argumentCaptor.getValue();
        Assert.assertNotNull("null", userDo);
        Assert.assertEquals("not equals", userVO.getId(), userDo.getId());
        Assert.assertEquals("not equals", userVO.getName(), userDo.getName());
        Assert.assertEquals("not equals", userVO.getDesc(), userDo.getDesc());


    }

}
