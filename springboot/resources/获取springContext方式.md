## 1.通过Spring提供的工具类获取ApplicationContext对象

需要能够获取HttpServletRequest对象

```
ApplicationContext ac1 = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
ApplicationContext ac2 = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
MyService service1 = (MyService)ac1.getBean("myService");//这是beanId. 
MyService service2 = (MyService)ac2.getBean("myService");

out.println("第一种方式1----:"+service1.getSystemTime());//获取系统时间
out.println("<br />------------<br />");
out.println("第一种方式2----:"+service2.getSystemTime());
```

## 2. 继承自抽象类ApplicationObjectSupport 
当启动web服务容器的时候，就将ApplicationContext注入到一个spring工具类的一个静态属性中，这样在普通类就可以通过这个工具类获取ApplicationContext，从而通过getBean( )获取bean对象

```
public final class ToolSpring extends ApplicationObjectSupport {
	private static ApplicationContext applicationContext = null;
	@Override
	protected void initApplicationContext(ApplicationContext context)
				throws BeansException {
			super.initApplicationContext(context);
			if(ToolSpring.applicationContext == null){
				ToolSpring.applicationContext = context;
			}
	}
	public static ApplicationContext getAppContext() {
		return applicationContext;
	}
	public static Object getBean(String name){
		return getAppContext().getBean(name);
	}
}
```

## 3. 继承自抽象类WebApplicationObjectSupport 
```
public final class ToolSpring extends WebApplicationObjectSupport{
	private static ApplicationContext applicationContext = null;
	@Override
	protected void initApplicationContext(ApplicationContext context) {
		super.initApplicationContext(context);
		if(applicationContext == null){
			applicationContext = context;
		}
	}
	public static ApplicationContext getAppContext() {
		return applicationContext;
	}
	public static Object getBean(String name){
		return applicationContext.getBean(name);
	}
}
```
### 4. 实现接口ApplicationContextAware
这种方式是实现接口的方式，本人比较喜欢这种方式，因为这种方式扩展性比较强，我们可以根据需要在加入其他的接口.

```
public final class ToolSpring implements ApplicationContextAware{
	private static ApplicationContext applicationContext = null;
	@Override
	public  void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if(ToolSpring.applicationContext == null){
			ToolSpring.applicationContext  = applicationContext;
			System.out.println("========ApplicationContext配置成功,在普通类可以通过调用ToolSpring.getAppContext()获取applicationContext对象,applicationContext="+applicationContext+"========");
		}
	}
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	public static Object getBean(String name){
		return getApplicationContext().getBean(name);
	}
}
```

## 在Spring Boot可以扫描的包下
写的工具类为SpringUtil，实现ApplicationContextAware接口，并加入Component注解，让spring扫描到该bean
```
@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }
    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    //通过name获取 Bean.
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }
    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }
    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
}
```
## 不在Spring Boot的扫描包下方式一

同上，编写SpringUtil， 在Spring boot应用中，使用`@Bean`注解进行构建对应的SpringUtil的bean。

## 使用@Import进行导入
`@Import(SpringUtil2.class)`




