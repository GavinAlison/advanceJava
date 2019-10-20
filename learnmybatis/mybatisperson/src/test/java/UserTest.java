import com.alison.mybatis.bean.Orders;
import com.alison.mybatis.bean.User;
import com.alison.mybatis.dao.OrdersMapperCustom;
import com.alison.mybatis.dao.UserMapper;
import com.alison.mybatis.tool.MyBatisUtil;
import com.alison.mybatis.vo.OrderCustom;
import com.alison.mybatis.vo.UserCustom;
import com.alison.mybatis.vo.UserQueryVo;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserTest {
    @Test
    public void TestFind() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        //创建包装对象，设置查询条件
        UserQueryVo userQueryVo = new UserQueryVo();
        UserCustom userCustom = new UserCustom();
        userCustom.setSex("男");
        userCustom.setUsername("zhangsan");
        userQueryVo.setUserCustom(userCustom);
        //调用UserMapper的方法
        List<UserCustom> list = userMapper.findUserList(userQueryVo);
        System.out.println(list);
        sqlSession.close();
    }

    //测试用户信息综合查询总数
    @Test
    public void testFindUserCount() throws Exception {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        //创建usermapper对象,mybatis自动生成代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        //创建包装对象，设置查询条件
        UserQueryVo userQueryVo = new UserQueryVo();
        UserCustom userCustom = new UserCustom();
        userCustom.setSex("男");
        userCustom.setUsername("zhangsan");
        userQueryVo.setUserCustom(userCustom);
        //调用UserMapper的方法
        System.out.println(userMapper.findUserCount(userQueryVo));
        sqlSession.close();
    }

    //测试根据id查询用户信息，使用 resultMap 输出
    @Test
    public void testFindUserByIdResultMap() throws Exception {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        //创建usermapper对象,mybatis自动生成代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        //调用UserMapper的方法
        User user = userMapper.findUserById(1);
        System.out.println(user);
        sqlSession.close();
    }

    @Test
    public void findUserListHe() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        //创建包装对象，设置查询条件
        UserQueryVo userQueryVo = new UserQueryVo();
        UserCustom userCustom = new UserCustom();
        userCustom.setSex("男");
//        userCustom.setUsername("zhangsan");
        userQueryVo.setUserCustom(userCustom);
        //调用UserMapper的方法
        UserCustom userCustom1 = userMapper.findUserListHe(userQueryVo);
        System.out.println(userCustom1);
        sqlSession.close();
    }

    @Test
    public void testFindOrdersUser() throws Exception {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        //创建OrdersMapperCustom对象,mybatis自动生成代理对象
        OrdersMapperCustom ordersMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);
        //调用OrdersMapperCustom的方法
        List<OrderCustom> list = ordersMapperCustom.findOrdersUser();
        System.out.println(list);
        sqlSession.close();
    }

    @Test
    public void testFindOrdersUserResultMap() throws Exception {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        //创建OrdersMapperCustom对象,mybatis自动生成代理对象
        OrdersMapperCustom ordersMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);
        //调用OrdersMapperCustom的方法
        List<Orders> list = ordersMapperCustom.findOrdersUserResultMap();
        System.out.println(list);
        sqlSession.close();
    }

    @Test
    public void testFindOrdersAndOrderDetailResultMap() throws Exception {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        //创建OrdersMapperCustom对象,mybatis自动生成代理对象
        OrdersMapperCustom ordersMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);
        //调用OrdersMapperCustom的方法
        List<Orders> list = ordersMapperCustom.findOrdersAndOrderDetailResultMap();
        System.out.println(list);
        sqlSession.close();
    }

    @Test
    public void testFindUserAndItemsResultMap() throws Exception {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        //创建OrdersMapperCustom对象,mybatis自动生成代理对象
        OrdersMapperCustom ordersMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);
        //调用OrdersMapperCustom的方法
        List<User> list = ordersMapperCustom.findUserAndItemsResultMap();
        System.out.println(list);
        sqlSession.close();
    }

    @Test
    public void testFindOrdersUserLazyLoading() throws Exception {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        //创建OrdersMapperCustom对象,mybatis自动生成代理对象
        OrdersMapperCustom ordersMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);
        //查询订单信息
        List<Orders> list = ordersMapperCustom.findOrdersUserLazyLoading();
        //遍历所查询的的订单信息
        for (Orders orders : list) {
            //查询用户信息
            User user = orders.getUser();
            System.out.println(user);
        }
        sqlSession.close();
    }

    //一级缓存测试
    @Test
    public void testCache1() throws Exception {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        //创建UserMapper对象,mybatis自动生成代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        //查询使用的是同一个session
        //第一次发起请求，查询Id 为1的用户信息
        User user1 = userMapper.findUserById(1);
        System.out.println(user1);
        //第二次发起请求，查询Id 为1的用户信息
        User user2 = userMapper.findUserById(1);
        System.out.println(user2);

        //如果sqlSession去执行commit操作（执行插入、更新、删除），
        // 清空SqlSession中的一级缓存，这样做的目的为了让缓存中存储的是最新的信息，避免脏读。
        //更新user1的信息，
        user1.setUsername("李飞");
        //user1.setSex("男");
        //user1.setAddress("北京");
        userMapper.updateUserById(user1);
        //提交事务,才会去清空缓存
        sqlSession.commit();

        user2 = userMapper.findUserById(1);
        System.out.println(user2);
        sqlSession.close();
    }

    //二级缓存测试
    @Test
    public void testCache2() throws Exception {
        SqlSession sqlSession1 = MyBatisUtil.getPrototypeSqlSession();
        SqlSession sqlSession2 = MyBatisUtil.getPrototypeSqlSession();
        SqlSession sqlSession3 = MyBatisUtil.getPrototypeSqlSession();


        //创建UserMapper对象,mybatis自动生成代理对象
        UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
        //sqlSession1 执行查询 写入缓存(第一次查询请求)
        User user1 = userMapper1.findUserById(1);
        System.out.println(user1);
        sqlSession1.close();
        System.out.println("----------------------------");

        //sqlSession3  执行提交  清空缓存
        UserMapper userMapper3 = sqlSession3.getMapper(UserMapper.class);
        User user3 = userMapper3.findUserById(1);
        user3.setSex("女");
        user3.setAddress("山东济南");
        user3.setUsername("崔建");
        userMapper3.updateUserById(user3);
        //提交事务，清空缓存
        sqlSession3.commit();
        sqlSession3.close();
        System.out.println("----------------------------");
        //sqlSession2 执行查询(第二次查询请求)
        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
        User user2 = userMapper2.findUserById(1);
        System.out.println(user2);
        sqlSession2.close();
    }
}
