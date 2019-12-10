class loader


# 一个类是如何加载进jvm中？

## 类的加载

通过类的加载机制， 过程：
加载--》 链接--》 初始化
		校验--》准备--》解析

一句话： 一个类在目录中，通常是将一个类全限定名的字符串给jvm，jvm 根据双亲委派加载class文件，
加载class文件到方法区中，对应的方法区中有对应的java.lang.class对象。

然后jvm 会校验对应的class文件的正确性，验证文件格式，元数据，字节码，符号引用

校验通过，开始准备，为class 对象准备空间，class对象对应的静态变量准备空间， 主要是分配内存

内存分配之后，开始解析，因为class文件存在一些符号引用和字面量，这些信息存储在方法区的常量池中。 
主要是解析这些字面量与符号引用。
将符号引用转换成直接引用， 字面量会在常量池中存在，并将常量池的引用给class文件对象。

初始化， 这一步就好像是赋值， static{}, {}, 实例化， 就是在上面已经分配的内存变量中赋值。有静态变量的，
有class对象普通变量的。

概括： 加载class文件--》验证Class文件--》分配内存--》解析符号与字面量--》初始化赋值

然后在方法区中存在class文件对象，可以根据这个进行实例化创建对象。

## java.lang.Class文件中forName方法的重载
-	 `public static Class<?> forName(String name, boolean initialize,
                   ClassLoader loader)
        throws ClassNotFoundException`
-	`public static Class<?> forName(String className) 
                throws ClassNotFoundException`


```
initialize: 如果为true，则会在返回Class对象之前，对该类型做连接，校验，初始化操作。
(如：执行static块中的代码)，initialize默认需要初始化。

loader:用自定义的类加载器来请求这个类型；当然，你也可以传入null，用bootstrap加载器

```

## ClassLoader.loadClass重载方法
```
protected synchronized Class<?> loadClass(String name, boolean resolve)
    throws ClassNotFoundException

public Class<?> loadClass(String name) throws ClassNotFoundException 
```

name:类的全限定名，如：com.org.prj

resolve：表示是否需要连接该类型。 仅仅是连接（这里面包括校验class文件，准备分配内存，类型常量池的替换），并不会初始化该类型。

resolve默认是不链接，不进行链接意味着不进行包括初始化等一些列步骤，那么静态块和静态对象就不会得到执行。

总结：

1.Class.forName返回的Class对象可以决定是否初始化。而ClassLoader.loadClass返回的类型绝对不会初始化，最多只会做连接操作。

2.Class.forName可以决定由哪个classLoader来请求这个类型。而ClassLoader.loadClass是用当前的classLoader去请求。




>https://www.cnblogs.com/clarke157/p/6651195.html

