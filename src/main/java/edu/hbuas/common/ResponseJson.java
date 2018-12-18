package edu.hbuas.common;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

@JsonSerialize(include =  JsonSerialize.Inclusion.NON_NULL)
//保证序列化json的时候,如果是null的对象,key也会消失
public class ResponseJson<T> implements Serializable {

    private int status;
    private String msg;
    private T data;

    private ResponseJson(int status,T data){
        this.status = status;
        this.data = data;
    }

    private ResponseJson(int status,String msg,T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private ResponseJson(int status,String msg){
        this.status = status;
        this.msg = msg;
    }

    @JsonIgnore
    //使之不在json序列化结果当中
    public boolean isSuccess(){
        return this.status == Const.ResponseCode.SUCCESS.getCode();
    }

    public int getStatus(){
        return status;
    }
    public T getData(){
        return data;
    }
    public String getMsg(){
        return msg;
    }

//    成功只有一个status
//    status, msg  此处不能重载，String 会被识别为泛型 T

    public static <T> ResponseJson<T> createSuccess(){
        return new ResponseJson<T>(Const.ResponseCode.SUCCESS.getCode(), Const.ResponseCode.SUCCESS.getDesc());
    }

//     status, data
    public static <T> ResponseJson<T> createBySuccess(T data){
        return new ResponseJson<T>(Const.ResponseCode.SUCCESS.getCode(),data);
    }
//  status, msg, data
    public static <T> ResponseJson<T> createBySuccess(String msg,T data){
        return new ResponseJson<T>(Const.ResponseCode.SUCCESS.getCode(),msg,data);
    }


//    错误status msg有很多个 格式为 status, msg
//    默认
    public static <T> ResponseJson<T> createByError(){
        return new ResponseJson<T>(Const.ResponseCode.ERROR.getCode(),Const.ResponseCode.ERROR.getDesc());
    }


    public static <T> ResponseJson<T> createByError(String errorMessage){
        return new ResponseJson<T>(Const.ResponseCode.ERROR.getCode(),errorMessage);
    }

    public static <T> ResponseJson<T> createByError(int errorCode,String errorMessage){
        return new ResponseJson<T>(errorCode,errorMessage);
    }













}
