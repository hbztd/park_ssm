package edu.hbuas.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ImageVerify {
    public static Map<String, Object> getImageVerify(){
        BufferedImage img = new BufferedImage(60,30,BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
//        背景颜色
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, 60, 30);

        StringBuffer randomCode = new StringBuffer();
        Random random = new Random();
        for(int i=0; i<4; i++) {
            //生成0-9 随机验证数字
            int a = random.nextInt(10);
            // 数字的y坐标
            int y = 10 + random.nextInt(20);
            g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256))); // (255,255,255)
            g.drawString(a+"",i*10,y);
            randomCode.append(a);
        }
        for(int i=0;i<10; i++) {
            g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            g.drawLine(10+random.nextInt(60),5+random.nextInt(30), 10+random.nextInt(60),5+random.nextInt(30) );
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", randomCode.toString());
        map.put("codePic", img);
        return map;
    }
}
