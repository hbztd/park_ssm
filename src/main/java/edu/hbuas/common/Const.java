package edu.hbuas.common;

import java.util.HashMap;
import java.util.Map;

public interface Const {
    public enum ResponseCode {

        SUCCESS(0,"SUCCESS"),
        ERROR(1,"ERROR"),
        NEED_LOGIN(10,"NEED_LOGIN"),
        UNAUTHORIZED(2,"UNAUTHORIZED");

        private final int code;
        private final String desc;


        ResponseCode(int code,String desc){
            this.code = code;
            this.desc = desc;
        }

        public int getCode(){
            return code;
        }
        public String getDesc(){
            return desc;
        }

    }

    public enum RoleCode{

        R1("r001", "超级管理员"),
        R2("r002", "普通管理员");

        private final String code;
        private final String desc;

        RoleCode(String code, String desc){
            this.code = code;
            this.desc = desc;
        }

        public String getCode(){
            return code;
        }

        public String getDesc(){
            return desc;
        }

    }

    public String salt = "postmalonesdafaqj23ou89ZXcj@#$@#$#@KJdjklj;D../dSF.";

    public double price = 0.05;
}
