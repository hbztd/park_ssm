package edu.hbuas.service;

import edu.hbuas.common.ResponseJson;
import edu.hbuas.pojo.UPermission;
import edu.hbuas.pojo.User;
import edu.hbuas.vo.FindView;

import java.util.List;
import java.util.Map;

public interface UserService {
    ResponseJson login(String username, String password);
    Map<String, Object> getVerifyImage();
    ResponseJson register(User user);
    ResponseJson verifyCode(String code, String codeVerify);
    ResponseJson checkValid(String str);
    ResponseJson<String> changePwd(String oldPwd, String newPwd);

    ResponseJson<String> deleteUser(String account);
    ResponseJson<String> updateUser(User card);
    ResponseJson selectByTypeAndPage(FindView findView);
    ResponseJson<Integer> selectAllRecords();
    ResponseJson<Integer> selectRecords(FindView findView);

    ResponseJson<List<String>> selectAllPermission(String account);
    ResponseJson<String> addUserPermission(UPermission uPermission);
    ResponseJson<String> removeUserPermission(UPermission uPermission);
}
