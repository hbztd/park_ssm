package edu.hbuas.service.impl;

import edu.hbuas.common.ResponseJson;
import edu.hbuas.dao.SeatDao;
import edu.hbuas.pojo.Seat;
import edu.hbuas.service.RedisService;
import edu.hbuas.service.SeatService;
import edu.hbuas.util.TimeUtil;
import edu.hbuas.vo.FindView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service("SeatService")
public class SeatServiceImpl implements SeatService {

    private final SeatDao seatDao;
    private final RedisService redisService;

    @Autowired
    public SeatServiceImpl(SeatDao seatDao, RedisService redisService) {
        this.seatDao = seatDao;
        this.redisService = redisService;
    }

    @Override
    public ResponseJson<String> insertSeat(Seat seat) {
//        此处可以考虑改为ajax 动态验证
        System.out.println(seat.getSeatNum());
        System.out.println(redisService.seatIsExists(seat.getSeatNum()));
        if(redisService.seatIsExists(seat.getSeatNum())){
            return ResponseJson.createByError("该车位编号已存在");
        }
        seat.setSeatId("S"+TimeUtil.getTimestamp());
        seat.setSeatState(0);
        seat.setSeatType(1);
//        新增一个车位
        if(seatDao.insertSelective(seat) > 0) {
//            更新缓存
            redisService.addSeatFreeItem(seat.getSeatNum()+"#"+seat.getSeatId());
            redisService.addSeatItem(seat.getSeatNum());
            return ResponseJson.createBySuccess("添加成功");
//            添加成功 更新缓存
//            String item = seat.getSeatId() + "#" + seat.getSeatNum();
//            redisCacheManager.addSeatAllFreeItem(item);
//            if(seat.getSeatType() == 1){
//                redisCacheManager.addSeatAllFixedFreeItem(item);
//            } else if(seat.getSeatType() == 2) {
//                redisCacheManager.addSeatAllTempFreeItem(item);
//            }

        }
        return ResponseJson.createByError("添加失败");
    }

    @Override
    public ResponseJson<String> deleteSeat(Seat seat) {
//        TODO 缓存Seat
        String seatId = seat.getSeatId();
        Seat seat2 = seatDao.selectByPrimaryKey(seatId);
        if(seat2.getSeatType() == 2){ // seatStatus == 1
            return ResponseJson.createByError("该车位已经卖出，无法删除");
        }
        seat.setSeatType(3);
        if(seatDao.updateByPrimaryKeySelective(seat) > 0) {
            redisService.removeSeatItem(seat.getSeatNum());
            redisService.removeSeatFreeItem(seat.getSeatNum()+"#"+seatId);
            return ResponseJson.createBySuccess("删除成功");
        }
        return ResponseJson.createByError("删除失败");
    }

    @Override
    public ResponseJson<String> updateSeat(Seat seat) {
        Seat seat2 = seatDao.selectByPrimaryKey(seat.getSeatId());
        if(redisService.seatIsExists(seat.getSeatNum()) && !seat2.getSeatNum().equals(seat.getSeatNum())) {
            return ResponseJson.createByError("该车位编号已存在");
        }
        String item = seat.getSeatNum() + "#" + seat.getSeatId();
        String item2 = seat2.getSeatNum() + "#" + seat2.getSeatId();
        if(seatDao.updateByPrimaryKeySelective(seat)>0) {
            if(!item.equals(item2)) {
//                删除之前
                redisService.removeSeatFreeItem(item2);
                redisService.removeSeatItem(seat2.getSeatNum());
//                更新现在的
                redisService.addSeatFreeItem(item);
                redisService.addSeatItem(seat.getSeatNum());
            }
            return ResponseJson.createBySuccess("更新成功");
        }
        return ResponseJson.createByError("更新失败");
    }

    @Override
    public ResponseJson selectByTypeAndPage(FindView findView) {
        List<Seat> list = seatDao.selectByTypeAndPage(findView);
        if(list != null && list.size() > 0){
            return ResponseJson.createBySuccess("查询成功",list);
        }
        return ResponseJson.createByError("查询结果为空");
    }

    @Override
    public ResponseJson<Integer> selectAllRecords() {
        return ResponseJson.createBySuccess(seatDao.selectAllRecords());
    }

    @Override
    public ResponseJson<Integer> selectRecords(FindView findView) {
        return ResponseJson.createBySuccess(seatDao.selectRecords(findView));
    }


}
