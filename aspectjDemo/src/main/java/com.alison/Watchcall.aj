package com.alison;

/**
 * @Author alison
 * @Date 2019/11/17  17:55
 * @Version 1.0
 * @Description
 */
public aspect Watchcall {

    pointcut myConstructor(): execution(com.alison.Client.new(..));

    before(): myConstructor() {
        System.out.println("Entering Constructor");
    }
}

/**
 * aspect Watchcall 转换成class文件， 使用注解@Aspect注解该class
 *
 * @Aspect  这个的作用是什么， 它的注解实现在哪里
 * public class Watchcall {
 *     static {
 *         try {
 *         静态块 初始化实例，
 *             ajc$postClinit();
 *         } catch (Throwable var1) {
 *             ajc$initFailureCause = var1;
 *         }
 *
 *     }
 *
 *     public Watchcall() {
 *     }
 *
 *     @Before(
 *         value = "myConstructor()",
 *         argNames = ""
 *     )
 *     public void ajc$before$com_alison_Watchcall$1$cbdc802f() {
 *         System.out.println("Entering Constructor");
 *     }
 *
 *     public static Watchcall aspectOf() {
 *         if (ajc$perSingletonInstance == null) {
 *             throw new NoAspectBoundException("com_alison_Watchcall", ajc$initFailureCause);
 *         } else {
 *             return ajc$perSingletonInstance;
 *         }
 *     }
 *      判断是够有该实例 ajc$perSingletonInstance
 *     public static boolean hasAspect() {
 *         return ajc$perSingletonInstance != null;
 *     }
 * }
 */
