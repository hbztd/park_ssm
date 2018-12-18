package edu.hbuas.service.impl;

import edu.hbuas.common.ResponseJson;
import edu.hbuas.dao.CardDao;
import edu.hbuas.dao.SeatDao;
import edu.hbuas.pojo.Card;
import edu.hbuas.pojo.Seat;
import edu.hbuas.service.CardService;
import edu.hbuas.service.RedisService;
import edu.hbuas.util.TimeUtil;
import edu.hbuas.vo.CardView;
import edu.hbuas.vo.FindView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service("CardService")
public class CardServiceImpl implements CardService {

    private final CardDao cardDao;
    private final RedisService redisService;
    private final SeatDao seatDao;

    @Autowired
    public CardServiceImpl(CardDao cardDao, RedisService redisService, SeatDao seatDao) {
        this.cardDao = cardDao;
        this.redisService = redisService;
        this.seatDao = seatDao;
    }

    @Override
    public ResponseJson<String> insertCard(Card card) {
//        alter table `databaseName`.`table` add unique (不能重复的列名)
        card.setCardId("C"+TimeUtil.getTimestamp());
        if(cardDao.insertSelective(card) > 0) {
//            更新车位状态
            Seat seat = seatDao.selectByPrimaryKey(card.getSeatId());
            seat.setSeatState(1);
            seat.setSeatType(2);
            seatDao.updateByPrimaryKeySelective(seat); // TODO 增加事务处理
//            更新缓存
            redisService.removeSeatFreeItem(seat.getSeatNum()+"#"+seat.getSeatId());
            redisService.addAllCardItem(card.getCardId()+"#"+card.getUserName());
            return ResponseJson.createBySuccess("添加成功");
        }
        return ResponseJson.createByError("添加失败");
    }

    @Override
    public ResponseJson<String> deleteCard(Card card) {
        if(cardDao.deleteByChangeStatus(card.getCardId()) > 0) {
//            更新车位状态
            Seat seat = seatDao.selectByPrimaryKey(card.getSeatId());
            seat.setSeatState(0);
            seat.setSeatType(1);
            seatDao.updateByPrimaryKeySelective(seat);
//            更新缓存
            redisService.addSeatFreeItem(seat.getSeatNum()+"#"+seat.getSeatId());
            redisService.removeAllCardItem(card.getCardId()+"#"+card.getUserName());
            return ResponseJson.createBySuccess("删除成功");
        }
        return ResponseJson.createByError("删除失败");
    }

    @Override
    public ResponseJson<String> updateCard(Card card) {
        Card card2 = cardDao.selectByPrimaryKey(card.getCardId());
        if(cardDao.updateByPrimaryKeySelective(card)>0) {
            if(!card.getSeatId().equals(card2.getSeatId())) {
//                删除之前的
                Seat seat = seatDao.selectByPrimaryKey(card2.getSeatId());
                redisService.removeSeatFreeItem(seat.getSeatNum()+"#"+seat.getSeatId());
                redisService.removeAllCardItem(card2.getCardId()+"#"+card2.getUserName());
//                加入更新的
                Seat seat2 = seatDao.selectByPrimaryKey(card.getSeatId());
                redisService.addSeatFreeItem(seat2.getSeatNum()+"#"+seat2.getSeatId());
                redisService.addAllCardItem(card.getCardId()+"#"+card.getUserName());
            }
            return ResponseJson.createBySuccess("更新成功");
        }
        return ResponseJson.createByError("更新失败");
    }

    @Override
    public ResponseJson<Set<Object>> getSeatFree(){
        Set<Object> set = redisService.getAllSeatFree();
        return ResponseJson.createBySuccess(set);
    }

    @Override
    public ResponseJson<Set<Object>> getAllCard(){
        return redisService.getAllCard();
    }
    @Override
    public ResponseJson selectByTypeAndPage(FindView findView) {
        List<CardView> list = cardDao.selectByTypeAndPage(findView);
        if(list != null && list.size() > 0){
            return ResponseJson.createBySuccess("查询成功",list);
        }
        return ResponseJson.createByError("查询结果为空");
    }

    @Override
    public ResponseJson<Integer> selectAllRecords() {
        return ResponseJson.createBySuccess(cardDao.selectAllRecords());
    }

    @Override
    public ResponseJson<Integer> selectRecords(FindView findView) {
        return ResponseJson.createBySuccess(cardDao.selectRecords(findView));
    }
}
