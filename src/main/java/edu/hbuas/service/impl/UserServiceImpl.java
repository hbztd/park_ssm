package edu.hbuas.service.impl;

import edu.hbuas.common.ResponseJson;
import edu.hbuas.dao.UserDao;
import edu.hbuas.pojo.User;
import edu.hbuas.service.UserService;
import edu.hbuas.util.ImageVerify;
import edu.hbuas.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.Map;

@Service("UserService")
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public ResponseJson login(String username, String password) {
//        此处加盐
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()) {
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
            user.setUserPwd(MD5Util.MD5Encode(user.getUserPwd()+user.getAccount()));
            if(userDao.insert(user)>0) {
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
}
