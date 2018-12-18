package edu.hbuas.service.impl;

import edu.hbuas.common.Const;
import edu.hbuas.common.ResponseJson;
import edu.hbuas.dao.TempDao;
import edu.hbuas.pojo.Temp;
import edu.hbuas.service.RedisService;
import edu.hbuas.service.TempService;
import edu.hbuas.util.BigDecimalUtil;
import edu.hbuas.util.TimeUtil;
import edu.hbuas.vo.FindView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service("TempService")
public class TempServiceImpl implements TempService {
    private final TempDao tempDao;
    private final RedisService redisService;

    @Autowired
    public TempServiceImpl(TempDao tempDao, RedisService redisService) {
        this.tempDao = tempDao;
        this.redisService = redisService;
    }

    @Override
    public ResponseJson<String> insertTemp(String carNum) {
        if(StringUtils.isEmpty(carNum)) {
            System.out.println("参数错误");
            return ResponseJson.createByError("参数错误");
        }
        Temp temp = new Temp();
        temp.setCardId(carNum);
        temp.setCarNum(carNum);
        Map map = TimeUtil.getDateTime();
        temp.setEntryDate((String)map.get("localDate"));
        temp.setEntryTime((String)map.get("localTime"));
        temp.setTempMoney(BigDecimalUtil.getBigDecimal(0));
        temp.setTempId("T"+map.get("timestamp"));
        redisService.setTimeEntryCache(temp.getTempId(),(long)map.get("timestamp"));
        if(tempDao.insertSelective(temp) > 0) {
            return ResponseJson.createBySuccess("进入停车场成功");
        }
        return ResponseJson.createByError("进入停车场失败");
    }

    @Override
    public ResponseJson<String> insertTempOut(String tempId) {
        Temp temp = new Temp();
        Map map = TimeUtil.getDateTime();
        temp.setTempId(tempId);
        temp.setOutDate((String)map.get("localDate"));
        temp.setOutTime((String)map.get("localTime"));
//        redis缓存丢失
        long timestamp;
        if(redisService.timeEntryCacheIsExists(tempId)) {
            timestamp = (long)map.get("timestamp") - redisService.getTimeEntryCache(tempId);
        } else {
            timestamp = (long)map.get("timestamp") - new Long(tempId.split("T")[1]);
        }

        double interval = timestamp/(1000*60);
        BigDecimal money = BigDecimalUtil.mul(Const.price,interval);
        temp.setTempMoney(money);
        System.out.println(temp);
        if(tempDao.updateByPrimaryKeySelective(temp) > 0) {
            return ResponseJson.createBySuccess("退出停车场成功");
        }
        return ResponseJson.createByError("退出停车场失败");
    }

    @Override
    public ResponseJson<String> deleteTemp(String tempId) {
        if(tempDao.deleteByChangeStatus(tempId) > 0) {
            return ResponseJson.createBySuccess("删除成功");
        }
        return ResponseJson.createByError("删除失败");
    }


    @Override
    public ResponseJson<List<Temp>> selectAll(FindView findView) {
        List<Temp> list = tempDao.selectByTypeAndPage(findView);
        if(list != null && list.size() > 0){
            return ResponseJson.createBySuccess("查询成功",list);
        }
        return ResponseJson.createByError("查询失败");
    }

    @Override
    public ResponseJson<Integer> selectAllRecords() {
        return ResponseJson.createBySuccess(tempDao.selectAllRecords());
    }

    @Override
    public ResponseJson<Integer> selectRecords(FindView findView) {
        return ResponseJson.createBySuccess(tempDao.selectRecord(findView));
    }

}
