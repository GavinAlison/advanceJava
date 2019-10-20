package com.alison.mybatis.dao;


import com.alison.mybatis.bean.Orders;
import com.alison.mybatis.bean.User;
import com.alison.mybatis.vo.OrderCustom;

import java.util.List;

public interface OrdersMapperCustom {
    public List<OrderCustom> findOrdersUser() throws Exception;

    public List<Orders> findOrdersUserResultMap() throws Exception;

    public List<Orders> findOrdersAndOrderDetailResultMap() throws Exception;

    public List<User> findUserAndItemsResultMap() throws Exception;

    public List<Orders> findOrdersUserLazyLoading() throws Exception;

    public void updateUserById(User user) throws Exception;
}
