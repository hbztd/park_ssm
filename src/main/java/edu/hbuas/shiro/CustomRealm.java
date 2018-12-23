package edu.hbuas.shiro;


import edu.hbuas.common.Const;
import edu.hbuas.dao.UPermissionDao;
import edu.hbuas.dao.UserDao;
import edu.hbuas.pojo.UPermission;
import edu.hbuas.pojo.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CustomRealm extends AuthorizingRealm {

// bug =>  autowired 只能对下面第一个有效！！！！！！！！！
    @Autowired
    private UPermissionDao uPermissionDao;
    @Autowired
    private UserDao userDao;


//    实现认证   Authentication 认证;身份验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println(authenticationToken);
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        Object principal = token.getPrincipal();
        System.out.println(principal);
        String credentials = userDao.getPwd(token.getUsername()); //查询的密码
        System.out.println(credentials);
        String realmName = this.getName();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,credentials,realmName);
        System.out.println("认证");
        return info;
    }

//    实现授权  Authorization 授权，批准
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Object principal = principalCollection.getPrimaryPrincipal(); // 获取登录的用户名
//        先判断用户角色 管理员直接赋予角色admin 其他查表赋予权限
        User user = userDao.selectByPrimaryKey((String) principal);
        String role = user.getRoleId();
        System.out.println("开始授权");
        if(role.equals(Const.RoleCode.R1.getCode())) {
            info.addRole("admin");
        } else {
            System.out.println("test1");
            String account = (String) principal;
            System.out.println(account);
            List<UPermission> list = uPermissionDao.selectAllPermissionByAccount(account);
            System.out.println("test2");
            List<String> list2 = new ArrayList<>();
            for(UPermission u:list){
                String name = uPermissionDao.selectPermissionUrl(u.getPid());
                list2.add(name);
            }
//        从权限表中得到map
            System.out.println(list2);
//            java.lang.IllegalArgumentException: Wildcard string cannot be null or empty 此处不能为空
            info.addStringPermissions(list2);
            info.addRole("user");
        }
        System.out.println("授权");
        return info;
    }
}
