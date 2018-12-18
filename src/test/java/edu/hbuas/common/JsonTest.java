package edu.hbuas.common;

import org.junit.Test;

public class JsonTest {

    @Test
    public void JTest(){
        ResponseJson json = ResponseJson.createByError("成功了");
        System.out.println(json.getMsg());
        System.out.println(json.getData());
        ResponseJson json1 = ResponseJson.createBySuccess("成功");
        System.out.println(json1.getMsg());
        System.out.println(json1.getData());
    }
}
