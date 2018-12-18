package edu.hbuas.shiro;


import edu.hbuas.common.Const;
import edu.hbuas.dao.UserDao;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserDao userDao;

//    实现认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println(authenticationToken);
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        Object principal = token.getPrincipal(); //前台传来的密码
        System.out.println(principal);
        String credentials = userDao.getPwd(token.getUsername()); //查询的密码
        String realmName = this.getName();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,credentials,realmName);
        return info;
    }

//    实现授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Object principal = principalCollection.getPrimaryPrincipal(); // 获取登录的用户名
        List<String> list = userDao.selectAllPermissionByAccount((String) principal);
//        从权限表中得到map
        if(principal.equals("admin")) {
            info.addRole("admin");
        }
        if(principal.equals("user")) {
            info.addRole("list");
        }
        info.addRole("user");
        return info;
    }
}
