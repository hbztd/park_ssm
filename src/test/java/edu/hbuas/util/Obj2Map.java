package edu.hbuas.util;

import edu.hbuas.vo.FindView;
import org.apache.commons.beanutils.BeanMap;
import org.junit.Test;

import java.util.Map;

public class Obj2Map {
    @Test
    public void obj2Map(){
        FindView findView = new FindView();
        findView.setSize(23);
        findView.setSortName("你哈啊");
        findView.setSortWay("哈哈");
        Map map = new BeanMap(findView);
        System.out.println(map);
    }
}
