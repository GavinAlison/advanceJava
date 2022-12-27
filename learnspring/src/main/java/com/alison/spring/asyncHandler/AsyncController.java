package com.alison.spring.asyncHandler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.Callable;

@Controller
public class AsyncController {
    /**
     * 过程解析：
     *         1、控制器返回Callable；
     *         2、Spring异步处理，将Callable提交到TaskExecutor 使用一个隔离器的线程进行执行；
     *      3、DispatcherServlet和所有的Filter退出web容器的线程；但是response保持打开状态
     *      4、Callable返回结果，SpringMvc将请求重新派发给容器，恢复之前的处理；
     *      5、根据Callable返回结果。SpringMVC继续进行视图渲染流程等（从受请求---》视图渲染）
     * ############测试结果############################################
     *         preHandle------------------>在目标运行之前运行:http://localhost:8080/mvc/async
     主线程开始。。。。。。。。。。。。。。。。。。Thread[http-nio-8080-exec-4,5,main]==>1560258412199
     主线程结束。。。。。。。。。。。。。。。。。。Thread[http-nio-8080-exec-4,5,main]==>1560258412204
     副线程开始。。。。。。。。。。。。。。。。。。Thread[MvcAsync1,5,main]==>1560258412222
     副线程结束。。。。。。。。。。。。。。。。。。Thread[MvcAsync1,5,main]==>1560258414223
     preHandle------------------>在目标运行之前运行:http://localhost:8080/mvc/async
     postHandle-------------->在目标运行之后运行(Callable之前的返回值就是目标方法的返回值)
     afterCompletion--------------->在页面响应后运行

     */
    @ResponseBody
    @RequestMapping("/async")
    public Callable<String> asynCallable(){
        System.out.println("主线程开始。。。。。。。。。。。。。。。。。。"+Thread.currentThread()+"==>"+System.currentTimeMillis());
        Callable<String> callable = new Callable<String>() {

            @Override
            public String call() throws Exception {
                System.out.println("副线程开始。。。。。。。。。。。。。。。。。。"+Thread.currentThread()+"==>"+System.currentTimeMillis());
                Thread.sleep(2000);
                System.out.println("副线程结束。。。。。。。。。。。。。。。。。。"+Thread.currentThread()+"==>"+System.currentTimeMillis());
                return "Callable<String> asynCallable";
            }
        };
        System.out.println("主线程结束。。。。。。。。。。。。。。。。。。"+Thread.currentThread()+"==>"+System.currentTimeMillis());
        return callable;
    }
}
