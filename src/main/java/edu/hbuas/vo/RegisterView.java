package edu.hbuas.vo;

import edu.hbuas.common.Const;
import edu.hbuas.pojo.User;

public class RegisterView {
    private String account;
    private String role;
    private String username;
    private String realname;
    private String password;
    private String phone;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "RegisterView{" +
                "account='" + account + '\'' +
                ", role='" + role + '\'' +
                ", username='" + username + '\'' +
                ", realname='" + realname + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

//    TODO 检查权限
    public User getUser(){
        User user = new User();
        user.setAccount(account);
        user.setUserName(username);
        user.setRealName(realname);
        user.setUserPwd(password);
        user.setUserPhone(phone);
        if(role.equals(Const.RoleCode.R2.getDesc())) {
            user.setRoleId("r002");
        }
        return user;
    }
}
