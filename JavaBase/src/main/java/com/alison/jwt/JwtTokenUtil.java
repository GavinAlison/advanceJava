package com.alison.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author alison
 * @Date 2020/5/21
 * @Version 1.0
 * @Description
 */
public class JwtTokenUtil {

    //用于生成token
    @Test
    public void test1() {
        JwtBuilder builder = Jwts.builder().setId("888").setSubject("小白")
                .setIssuedAt(new Date())//设置签发时间
                .signWith(SignatureAlgorithm.HS256, "xiaocai");//设置签名秘钥
        System.out.println(builder.compact());
    }

    //Token的解析
    @Test
    public void test2() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1OTAwMjc3MDZ9.qMRjqTd0tUmLz-B9oDgUNBHPQVRdyJtQYWwkBqz-9Ss";
        Claims claims = Jwts.parser().setSigningKey("xiaocai").parseClaimsJws(token).getBody();
        System.out.println("id:" + claims.getId());
        System.out.println("subject:" + claims.getSubject());
        System.out.println("IssuedAt:" + claims.getIssuedAt());
    }

    //token过期校验
    @Test
    public void test3() {
        //为了方便测试，我们将过期时间设置为1分钟
        long now = System.currentTimeMillis();//当前时间
        long exp = now + 1000 * 60;//过期时间为1分钟
        JwtBuilder builder = Jwts.builder().setId("888")
                .setSubject("小白")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "xiaocai")
                .setExpiration(new Date(exp));//设置过期时间
        System.out.println(builder.compact());
    }

    //ParseJwtTest
    @Test
    public void test4() {
        String compactJws = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1OTAwMjc3OTIsImV4cCI6MTU5MDAyNzg1Mn0.e9wY5wppnfRkIMgM7LpUh0i_2Xcu-qhiREE0UV52RZQ";
        Claims claims = Jwts.parser()
                .setSigningKey("xiaocai")
                .parseClaimsJws(compactJws).getBody();
        System.out.println("id:" + claims.getId());
        System.out.println("subject:" + claims.getSubject());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy‐MM‐dd hh:mm:ss");
        System.out.println("签发时间:" + sdf.format(claims.getIssuedAt()));
        System.out.println("过期时间:" + sdf.format(claims.getExpiration()));
        System.out.println("当前时间:" + sdf.format(new Date()));
    }

    //自定义claims
    @Test
    public void test5() {
        //为了方便测试，我们将过期时间设置为1分钟
        long now = System.currentTimeMillis();//当前时间
        long exp = now + 1000 * 60;//过期时间为1分钟
        JwtBuilder builder = Jwts.builder().setId("888")
                .setSubject("小白")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "xiaocai")
                .setExpiration(new Date(exp))
                .claim("roles", "admin")
                .claim("logo", "logo.png");
        System.out.println(builder.compact());
    }

    //自定义claims
    @Test
    public void test6() {
        String compactJws = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1OTAwMjgyNTUsImV4cCI6MTU5MDAyODMxNSwicm9sZXMiOiJhZG1pbiIsImxvZ28iOiJsb2dvLnBuZyJ9.qWT3gwcRrkC736J9LjyfdVvmSssN0tM_OuTRtyxYvBI";
        Claims claims = Jwts.parser().setSigningKey("xiaocai").parseClaimsJws(compactJws).getBody();
        System.out.println("id:" + claims.getId());
        System.out.println("subject:" + claims.getSubject());
        System.out.println("roles:" + claims.get("roles"));
        System.out.println("logo:" + claims.get("logo"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy‐MM‐dd hh:mm:ss");
        System.out.println("签发时间:" + sdf.format(claims.getIssuedAt()));
        System.out.println("过期时 间:" + sdf.format(claims.getExpiration()));
        System.out.println("当前时间:" + sdf.format(new Date()));
    }


}
