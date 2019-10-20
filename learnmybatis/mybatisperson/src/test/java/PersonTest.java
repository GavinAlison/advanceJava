import com.alison.mybatis.bean.Person;
import com.alison.mybatis.dao.PersonDao;
import com.alison.mybatis.dao.PersonDaoImpl;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

public class PersonTest {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void setup() throws Exception {
        String resource = "mybatis-config.xml";
        //得到配置文件流
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //创建会话工厂,传入Mybatis的配置文件信息
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testFindUserById() throws Exception {
        //创建UserDao的对象
        PersonDao userDao = new PersonDaoImpl(sqlSessionFactory);
        //调用UserDao方法
        Person person = userDao.findPersonById(1);
        System.out.println(person);
    }
}
