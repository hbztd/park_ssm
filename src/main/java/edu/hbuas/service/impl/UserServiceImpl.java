package edu.hbuas.service.impl;

import edu.hbuas.common.ResponseJson;
import edu.hbuas.dao.UPermissionDao;
import edu.hbuas.dao.UserDao;
import edu.hbuas.pojo.UPermission;
import edu.hbuas.pojo.User;
import edu.hbuas.service.UserService;
import edu.hbuas.util.ImageVerify;
import edu.hbuas.util.MD5Util;
import edu.hbuas.vo.FindView;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("UserService")
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UPermissionDao uPermissionDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, UPermissionDao uPermissionDao) {
        this.userDao = userDao;
        this.uPermissionDao = uPermissionDao;
    }

    @Override
    public ResponseJson login(String username, String password) {
//        此处加盐
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()) {
            System.out.println(password+username);
            password = MD5Util.MD5Encode(password+username);
            try{
                UsernamePasswordToken token = new UsernamePasswordToken(username,password);
                subject.login(token);
            } catch (AuthenticationException e) {
                return ResponseJson.createByError("用户名或密码错误");
            }
        } else {
            return ResponseJson.createByError("请不要重复登录");
        }
        User user = userDao.selectByPrimaryKey(username);
        user.setUserPwd(StringUtils.EMPTY);
        return ResponseJson.createBySuccess("登录成功", user);
    }

    public Map<String, Object> getVerifyImage(){
        return ImageVerify.getImageVerify();
    }

    @Override
    public ResponseJson<String> register(User user) {
//        存在返回false 不存在返回true 此处要去反
        if(!checkValid(user.getAccount()).isSuccess()){
            return ResponseJson.createByError("用户名已存在");
        } else {
//            MD5加密 加入自己的账号名称
//            TODO 事务处理
            user.setUserPwd(MD5Util.MD5Encode(user.getUserPwd()+user.getAccount()));
            System.out.println(user.getUserPwd()+user.getAccount());
            user.setRoleId("r002"); //设为普通管理员
            if(userDao.insert(user)>0) {
                List<Integer> list = uPermissionDao.selectAllPermissionByDefault();
                for(int i: list) {
                    UPermission uPermission = new UPermission();
                    uPermission.setUid(user.getAccount());
                    uPermission.setPid(i);
                    uPermissionDao.insert(uPermission);
                }
                return ResponseJson.createBySuccess("注册成功");
            } else {
                return ResponseJson.createBySuccess("注册失败");
            }
        }

    }

    @Override
    public ResponseJson verifyCode(String code, String codeVerify) {
        if(StringUtils.isEmpty(code) && StringUtils.isEmpty(codeVerify)){
            System.out.println("失败");
            return ResponseJson.createByError();
        } else {
            if(code.equalsIgnoreCase(codeVerify)) {
                System.out.println("成功");
                return ResponseJson.createSuccess();
            } else {
                System.out.println("失败");
                return ResponseJson.createByError();
            }
        }
    }

    @Override
    public ResponseJson checkValid(String str) {
        if(userDao.checkAccount(str)>0){
            return ResponseJson.createByError();
        } else {
            return ResponseJson.createSuccess();
        }
    }

    @Override
    public ResponseJson<String> changePwd(String oldPwd, String newPwd) {
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        String oldPwd2 = userDao.getPwd(account);
        String md5 = MD5Util.MD5Encode(oldPwd+account);
        System.out.println(oldPwd);
        System.out.println(newPwd);
        System.out.println(md5);
        if(!md5.equals(oldPwd2)) {
            return ResponseJson.createByError("原密码输入不正确");
        }
        String md52 = MD5Util.MD5Encode(newPwd+account);
        User user = new User();
        user.setAccount(account);
        user.setUserPwd(md52);
        if (userDao.updateByPrimaryKeySelective(user)>0) {
            return ResponseJson.createBySuccess("修改密码成功");
        }
        return ResponseJson.createByError("修改密码失败");
    }

    @Override
    public ResponseJson<String> deleteUser(String account) {
        if(userDao.deleteByChangeStatus(account) > 0) {
            return ResponseJson.createBySuccess("删除成功");
        }
        return ResponseJson.createByError("删除失败");
    }

    @Override
    public ResponseJson<String> updateUser(User user) {
        String account = user.getAccount();
        String md5 = MD5Util.MD5Encode(account+account);
        user.setUserPwd(md5);
        if(userDao.updateByPrimaryKey(user)>0) {
            return ResponseJson.createBySuccess("重置成功");
        }
        return ResponseJson.createByError("重置失败");
    }

    @Override
    public ResponseJson selectByTypeAndPage(FindView findView) {
        List<User> list = userDao.selectByTypeAndPage(findView);
        System.out.println(list);
        if(list != null && list.size() > 0){
            return ResponseJson.createBySuccess("查询成功",list);
        }
        return ResponseJson.createByError("查询失败");
    }

    @Override
    public ResponseJson<Integer> selectAllRecords() {
        return ResponseJson.createBySuccess(userDao.selectAllRecords());
    }

    @Override
    public ResponseJson<Integer> selectRecords(FindView findView) {
        return ResponseJson.createBySuccess(userDao.selectRecords(findView));
    }

    @Override
    public ResponseJson<List<String>> selectAllPermission(String account) {
        List<UPermission> uPermissions = uPermissionDao.selectAllPermissionByAccount(account);
        System.out.println(uPermissions);
        List<String> list = new ArrayList<>();
        for(UPermission u: uPermissions) {
            int pid = u.getPid();
            list.add(uPermissionDao.selectPermissionName(pid));
        }
        return ResponseJson.createBySuccess(list);
    }

    @Override
    public ResponseJson<String> addUserPermission(UPermission uPermission) {
        int pid = uPermission.getPid();
        System.out.println(pid);
        if(uPermissionDao.permissionIsExist(uPermission)>0) {
            return ResponseJson.createByError("该用户已存在该权限");
        }
        if(uPermissionDao.insert(uPermission)>0) {
            return ResponseJson.createBySuccess("添加成功");
        }
        return ResponseJson.createByError("添加失败");
    }

    @Override
    public ResponseJson<String> removeUserPermission(UPermission uPermission) {
        if(uPermissionDao.permissionIsExist(uPermission) == 0) {
            return ResponseJson.createByError("该用户不存在该权限");
        }
        if(uPermissionDao.removeUserPermission(uPermission) > 0) {
            return ResponseJson.createBySuccess("删除成功");
        }
        return ResponseJson.createByError("删除失败");
    }


}
