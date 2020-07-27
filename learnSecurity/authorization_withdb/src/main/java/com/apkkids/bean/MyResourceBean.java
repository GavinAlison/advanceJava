package com.apkkids.bean;

import lombok.Data;
import org.springframework.stereotype.Component;


/**
 * 资源表resource对应的类
 */
@Data
@Component
public class MyResourceBean {
    private Long id;
    private String url;
    private String roles;

    public String[] getRolesArray(){
        String[] authorities = roles.split(",");
        return authorities;
    }

}
