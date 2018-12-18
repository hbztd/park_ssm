package edu.hbuas.controller;

import edu.hbuas.common.ResponseJson;
import edu.hbuas.pojo.User;
import edu.hbuas.service.UserService;
import edu.hbuas.util.MD5Util;
import edu.hbuas.util.RedisCacheManager;
import edu.hbuas.vo.RegisterView;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RedisCacheManager redisCacheManager;


    @Autowired
    public UserController(UserService userService, RedisCacheManager redisCacheManager) {
        this.userService = userService;
        this.redisCacheManager = redisCacheManager;
    }

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson login(String username, String password) {
        System.out.println(username);
        System.out.println(password);
//        redisCacheManager.set("test","hi");
        ResponseJson responseJson = userService.login(username,password);
        return responseJson;
    }

    @RequestMapping(value = "getCode.do", method = RequestMethod.GET)
    public void getVerifyCode(HttpSession session, HttpServletResponse response){
        Map<String, Object> map = userService.getVerifyImage();
        session.setAttribute("code", map.get("code"));

        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", -1);
        response.setContentType("image/jpg");
//        允许跨域
//        response.setHeader("Access-Control-Allow-Credentials","true");
//        response.setHeader("Access-Control-Allow-origin","http://localhost:8081");
//        response.setHeader("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS");
        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos;
        try {
            sos = response.getOutputStream();
            ImageIO.write((RenderedImage) map.get("codePic"), "jpg", sos);
            sos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "checkValid.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson checkValid(String account){
        System.out.println(account);
        return userService.checkValid(account);
    }

    @RequestMapping(value = "verifyCode.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson verifyCode(String code,HttpSession session) {
        String codeVerify = (String)session.getAttribute("code");
        System.out.println(codeVerify);
        return userService.verifyCode(code, codeVerify);
    }

    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson register(@RequestBody RegisterView registerView){
//        return userService.register(user);
        System.out.println(registerView);
        return userService.register(registerView.getUser());
    }


    @RequestMapping(value = "test.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson shiroLogin(String username, String password){
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject.getPrincipal());
        if(!subject.isAuthenticated()) {
            password = MD5Util.MD5Encode(password+username);
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            try{
                subject.login(token);
            } catch (AuthenticationException e) {
                System.out.println("登陆失败");
            }
        } else {
            System.out.println("请不要重复登陆");
        }
        return ResponseJson.createSuccess();
    }
}
