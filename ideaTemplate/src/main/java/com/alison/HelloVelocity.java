package com.alison;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HelloVelocity {

    public static void main(String[] args) throws IOException {
        String outPath = "D:\\workspace\\advanceJava\\ideaTemplate\\target\\hello.java";
        // 初始化模板引擎
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        // 获取模板文件
        Template t = ve.getTemplate("hellovelocity.vm");
        // 设置变量
        //创建VelocityContext上下文环境，并将user对象存放到VelocityContext中
        VelocityContext ctx = new VelocityContext();
        ctx.put("name", "Velocity");
        ctx.put("StringUtils", StringUtils.class);
        List list = new ArrayList();
        list.add("1");
        list.add("2");
        ctx.put("list", list);
        // 输出
        StringWriter sw = new StringWriter();
        t.merge(ctx, sw);
        File file = new File(outPath);
        try (
                BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outPath), "UTF-8"));//解决乱码
                PrintWriter pw = new PrintWriter(fw);
        ) {
            pw.println(sw.toString());
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
