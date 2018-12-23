package edu.hbuas.shiro;

import java.util.LinkedHashMap;

public class FilterChainDefinitionMapBuilder {
    public LinkedHashMap<String, String> linkedHashMap() {
//        anon可以匿名访问 authc必须授权才能访问
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("/index.html","anon");
        map.put("/css/**","anon");
        map.put("/js/**","anon");
        map.put("/fonts/**","anon");
        map.put("/favicon.ico","anon");
        map.put("/user/login.do","anon");
        map.put("/user/getCode.do","anon");
        map.put("/user/verifyCode.do","anon");
        map.put("/logout","logout");
        map.put("/","anon");
        map.put("/**","authc");

        return map;
    }
}
