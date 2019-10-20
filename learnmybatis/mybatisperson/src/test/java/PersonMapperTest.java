import com.alison.mybatis.bean.Person;
import com.alison.mybatis.bean.User;
import com.alison.mybatis.dao.PersonMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class PersonMapperTest {
    private SqlSessionFactory sqlSessionFactory;

    //此方法是在 testFindUserById 方法之前执行的
    @Before
    public void setup() throws Exception {
        //创建sqlSessionFactory
        //Mybatis 配置文件
        String resource = "mybatis-config.xml";
        //得到配置文件流
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //创建会话工厂,传入Mybatis的配置文件信息
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testFindUserById() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //创建userMapper对象,mybatis自动生成代理对象
        PersonMapper userMapper = sqlSession.getMapper(PersonMapper.class);
        //调用UserMapper的方法
        Person person = userMapper.findPersonById(1);
        System.out.println(person);
    }

    @Test
    public void testForeach() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
        List<Person> personList = personMapper.getPersonsByIds(new int[]{1, 2, 3, 4, 5});
        System.out.println(personList);
    }
}
