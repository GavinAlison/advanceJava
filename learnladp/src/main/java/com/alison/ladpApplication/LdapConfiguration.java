package com.alison.ladpApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class LdapConfiguration {

    private LdapTemplate ldapTemplate;

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        Map<String, Object> config = new HashMap();
//        contextSource.setUrl(LdapConstans.url);
//        contextSource.setBase(LdapConstans.BASE_DC);
//        contextSource.setUserDn(LdapConstans.username);
//        contextSource.setPassword(LdapConstans.password);
        //  解决 乱码 的关键一句
        config.put("java.naming.ldap.attributes.binary", "objectGUID");
        contextSource.setPooled(true);
        contextSource.setBaseEnvironmentProperties(config);
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        if (null == ldapTemplate)
            ldapTemplate = new LdapTemplate(contextSource());
        return ldapTemplate;
    }

}