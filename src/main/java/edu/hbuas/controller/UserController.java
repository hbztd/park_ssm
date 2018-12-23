package edu.hbuas.controller;

import edu.hbuas.common.Const;
import edu.hbuas.common.ResponseJson;
import edu.hbuas.pojo.UPermission;
import edu.hbuas.pojo.User;
import edu.hbuas.service.UserService;
import edu.hbuas.vo.FindView;
import org.apache.shiro.SecurityUtils;
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
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson login(String username, String password) {
        return userService.login(username,password);
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
//        允许跨域 图像可以直接跨域
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
//    @RequiresRoles(value = {"admin"})
    public ResponseJson register(@RequestBody User user){
//        return userService.register(user);
        Subject subject = SecurityUtils.getSubject();
        System.out.println(user);
        if(!subject.hasRole("admin")) {
            return ResponseJson.createByErrorNoPer();
        }
        return userService.register(user);
    }

    @RequestMapping(value = "update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson<String> update(@RequestBody User user) {
        Subject subject = SecurityUtils.getSubject();
        System.out.println(user);
        if(!subject.hasRole("admin")) {
            return ResponseJson.createByErrorNoPer();
        }
        return userService.updateUser(user);
    }

    @RequestMapping(value = "delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson<String> delete(String account) {
        Subject subject = SecurityUtils.getSubject();
        if(!subject.hasRole("admin")) {
            return ResponseJson.createByErrorNoPer();
        }
        return userService.deleteUser(account);
    }


    @RequestMapping(value = "find.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson find(@RequestBody FindView findUserView){
        System.out.println(findUserView);
        Subject subject = SecurityUtils.getSubject();
        if(!subject.hasRole("admin")) {
            return ResponseJson.createByErrorNoPer();
        }
        return userService.selectByTypeAndPage(findUserView);
    }

    @RequestMapping(value = "record.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson record(@RequestBody FindView findUserView){
        System.out.println(findUserView);
        Subject subject = SecurityUtils.getSubject();
        if(!subject.hasRole("admin")) {
            return ResponseJson.createByErrorNoPer();
        }
        return userService.selectRecords(findUserView);
    }

    @RequestMapping(value = "record2.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson recordAll(){
        Subject subject = SecurityUtils.getSubject();
        if(!subject.hasRole("admin")) {
            return ResponseJson.createByErrorNoPer();
        }
        return userService.selectAllRecords();
    }

    @RequestMapping(value = "findPermission.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson findPermission(String account) {
        Subject subject = SecurityUtils.getSubject();
        if(!subject.hasRole("admin")) {
            return ResponseJson.createByErrorNoPer();
        }
        return userService.selectAllPermission(account);
    }

    @RequestMapping(value = "addPermission.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson addPermission(@RequestBody UPermission uPermission) {
        Subject subject = SecurityUtils.getSubject();
        if(!subject.hasRole("admin")) {
            return ResponseJson.createByErrorNoPer();
        }
        System.out.println(uPermission);
        return userService.addUserPermission(uPermission);
    }

    @RequestMapping(value = "removePermission.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson removePermission(@RequestBody UPermission uPermission) {
        Subject subject = SecurityUtils.getSubject();
        if(!subject.hasRole("admin")) {
            return ResponseJson.createByErrorNoPer();
        }
        System.out.println(uPermission);
        return userService.removeUserPermission(uPermission);
    }

    @RequestMapping(value = "changePwd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson<String> changePassword(String oldPwd, String newPwd){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()) {
            return userService.changePwd(oldPwd,newPwd);
        }
        return ResponseJson.createByError("当前用户未认证");
    }

//    ResponseJson.createByError(Const.ResponseCode.UNAUTHORIZED.getCode(), Const.ResponseCode.UNAUTHORIZED.getDesc());

//    @RequestMapping(value = "unauthorized.do", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseJson unauthorized() {
//        return ResponseJson.createByError(Const.ResponseCode.UNAUTHORIZED.getCode(), Const.ResponseCode.UNAUTHORIZED.getDesc());
//    }

    @RequestMapping(value = "needLogin.do", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJson needLogin() {
        return ResponseJson.createByError(Const.ResponseCode.NEED_LOGIN.getCode(),Const.ResponseCode.NEED_LOGIN.getDesc());
    }
}
