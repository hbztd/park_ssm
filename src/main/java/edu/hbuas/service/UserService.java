package edu.hbuas.service;

import edu.hbuas.common.ResponseJson;
import edu.hbuas.pojo.User;

import java.util.Map;

public interface UserService {
    ResponseJson login(String username, String password);
    Map<String, Object> getVerifyImage();
    ResponseJson register(User user);
    ResponseJson verifyCode(String code, String codeVerify);
    ResponseJson checkValid(String str);

}
