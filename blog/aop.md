## 记录一下aop应用注解出现的问题
自定义注解，作用于方法上，实现效果，能在方法调用之后，实现给调用对应的api接口，实现将本次数据转存。

定义注解`Buried`
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Buried {
    String note() default ""; // 标注
    String operate() default ""; // 操作类型
    String dataKey() default ""; // 业务中数据表dataKey
}
```
定义切面`BuriedAspect`
```java
@Component
@Slf4j
public class BuriedAspect {
    private ThreadLocal<Long> beginTime = new ThreadLocal<>();
    // 注入异步调用处理器
    @Autowired
    private BuriedAsyncHandler buriedAsyncHandler;

    @Pointcut("@annotation(buried)")// 定义切点, 关联对应的注解
    public void serviceStatistics(Buried buried) {
    }
    // 定义前置通知关联的函数，参数是切入点，注解
    @Before(value = "serviceStatistics(buried)", argNames = "joinPoint,buried")
    public void doBefore(JoinPoint joinPoint, Buried buried) {
        // 记录请求到达时间
        beginTime.set(System.currentTimeMillis());
        log.debug("BuriedPoint note:{}", buried.note());
        // 开启异步调用
        buriedAsyncHandler.asyncHandle(joinPoint, buried.operate(), buried.dataKey());
    }
    // 定义后置通知， 关联对应函数，设置对应参数
    @After(value = "serviceStatistics(buried)", argNames = "buried")
    public void doAfter(Buried buried) {
        log.debug("BuriedPoint statistic time:{} ms, note:{}", System.currentTimeMillis() - beginTime.get(), buried.note());
    }
}
```
## TODO:
有个问题，这个@After与@Before中argNames的值可以随便写，还是有一定规则？

异步调用处理器
```
/**
 * 异步注解 @Async 在方法中使用，要抽取到单独的类中，配合 @EnableAsync 才能生效
 *
 * @author hanchaoyong
 */
@Service
@EnableAsync
@Slf4j
public class BuriedAsyncHandler {

    @Value("${point.url}")
    private String pointUrl = "";

    /**
     * 异步处理，参数转换JSON/上报数据采集接口
     *
     * @param joinPoint 注解点
     * @param operate   上报方式（INSERT/UPDATE）
     * @param dataKey   key
     * @return
     */
    @Async // 这个作用后台启动一个异步线程进行调用， 相应的处理类
    public Future<String> asyncHandle(JoinPoint joinPoint, String operate, String dataKey) {
        log.debug("BuriedPoint---------------asyncHandle-------------------------");
        //1.获取到所有的参数值的数组
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        //2.获取到方法的所有参数名称的字符串数组
        String[] parameterNames = methodSignature.getParameterNames();

        JSONObject object = new JSONObject();
        for (int i = 0, len = parameterNames.length; i < len; i++) {
            String json = JSON.toJSONString(args[i]);
            JSONObject o = JSONObject.parseObject(json);
            object.putAll(o);
            log.debug("BuriedPoint parameter name：" + parameterNames[i] + " = " + json);
        }
        System.out.println("BuriedPoint");
        log.debug("BuriedPoint pointUrl:{}, content:{}", pointUrl + "/" + operate + "/" + dataKey, object.toJSONString());
        try {
        	// 自己封装httpClient工具中调用post方法
            String response = HttpClientManagerBuilder.doPost(pointUrl + "/" + operate + "/" + dataKey, object.toJSONString());
            return new AsyncResult<>(response);
        } catch (Exception ex) {
            log.error("BuriedPoint doPost error, ", ex);

        }
        return new AsyncResult<>("BuriedPoint async post failure");
    }
}
```

使用buried 注意事项

```
 @Buried(note = "同步数据", operate = "INSERT", dataKey = "Qwbc8v6BuBgA1RCW")
    public void syncData(SpouserVO spouserVO) {
        log.debug("=====================");
    }
```

这个方法定义在serviceImpl类中，对应的接口需要有对应的方法。
如果对应的接口上没有相应的方法，该注解的实现不会走的。
思考为何会出现这种现象。

