package com.alison.rpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Set;

public class InvokerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 得到某接口下某个实现类的名字
     *
     * @param classInfo
     * @return
     * @throws Exception
     */
    private String getImplClassName(ClassInfo classInfo) throws Exception {
        // 服务方接口和实现类所在的包路径
        String interfacePath = "com.alison.rpc";
        int lastDot = classInfo.getClassName().lastIndexOf(".");
        String interfaceName = classInfo.getClassName().substring(lastDot);
        Class supperClass = Class.forName(interfacePath + interfaceName);
        Reflections reflection = new Reflections(interfacePath);
        // 得到某接口下的所有实现类
        Set<Class> implClassSet = reflection.getSubTypesOf(supperClass);
        if (implClassSet.size() == 0) {
            System.out.println("未找到实现类");
            return null;
        } else if (implClassSet.size() > 1) {
            System.out.println("找个多个实现类，未明确使用哪一个");
            return null;
        } else {
            // 把集合转换为数组
            Class[] classes = implClassSet.toArray(new Class[0]);
            // 得到实现类的名字
            return classes[0].getName();
        }
    }

    /**
     * 读取客户端发来的数据并通过反射调用实现类的方法
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ClassInfo classInfo = (ClassInfo) msg;
        Object clazz = Class.forName(getImplClassName(classInfo)).newInstance();
        Method method = classInfo.getClass().getMethod(classInfo.getMethodName(), classInfo.getTypes());
        // 通过反射调用实现类的方法
        Object result = method.invoke(clazz, classInfo.getObjects());
        ctx.writeAndFlush(result);
    }
}