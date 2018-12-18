package edu.hbuas.shiro;

import java.util.LinkedHashMap;

public class FilterChainDefinitionMapBuilder {
    public LinkedHashMap<String, String> linkedHashMap() {
//        anon可以匿名访问 authc必须授权才能访问
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
//        map.put("/user/login.do","anon");
//        map.put("/**","authc");

        return map;
    }
}
