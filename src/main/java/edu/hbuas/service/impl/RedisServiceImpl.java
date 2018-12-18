package edu.hbuas.service.impl;

import edu.hbuas.common.ResponseJson;
import edu.hbuas.dao.CardDao;
import edu.hbuas.dao.SeatDao;
import edu.hbuas.pojo.Card;
import edu.hbuas.pojo.Seat;
import edu.hbuas.service.RedisService;
import edu.hbuas.util.RedisCacheManager;
import edu.hbuas.vo.CardView;
import edu.hbuas.vo.FindView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

@Service("RedisServiceImpl")
public class RedisServiceImpl implements RedisService {

    private final RedisCacheManager redisCacheManager;
    private final CardDao cardDao;
//    private final FixedViewDao fixedViewDao;
    private final SeatDao seatDao;
//    private final SeatTempDao seatTempDao;
//
    @Autowired
    public RedisServiceImpl(RedisCacheManager redisCacheManager, CardDao cardDao, SeatDao seatDao) {
        this.redisCacheManager = redisCacheManager;
        this.cardDao = cardDao;
        this.seatDao = seatDao;
    }
//
//
    @PostConstruct
    @Override
    public void init() {
//        TODO ====
// 车主信息全部全部写入
        int size = cardDao.selectAllRecords();
//        int size2 = fixedViewDao.selectAllRecords();
        FindView findView = new FindView();
        findView.setSize(size);
        findView.setStart(0);
        List<CardView> list = cardDao.selectByTypeAndPage(findView);
        for (CardView l : list) {
            if(l.getCardStatus() == 0) {
                redisCacheManager.sSet("cardAll", l.getCardId() + "#" + l.getUserName());
            }
        }
        int size2 = seatDao.selectAllRecords();
        findView.setSize(size2);
        List<Seat> list2 = seatDao.selectByTypeAndPage(findView);
        for(Seat l : list2) {
            System.out.println(l);
            if(l.getSeatType() == 1 || l.getSeatType() == 2) {
                redisCacheManager.sSet("seatAll", l.getSeatNum());
                System.out.println(l.getSeatNum());
                if (l.getSeatState() == 0) {
                    redisCacheManager.sSet("seatAllFree", l.getSeatNum() + "#" + l.getSeatId());
                }
            }
        }
//        findView.setSize(size2);
//        List<FixedView> list2 = fixedViewDao.selectByTypeAndPage(findView);
//        for(FixedView l: list2) {
//            if(l.getOutDate() != null && l.getOutTime() != null) {
//                Map map = new BeanMap(l);
//                redisCacheManager.sSet("fixedEntry", l.getFixedId());
//                redisCacheManager.hmset(l.getFixedId(),map);
//            }
//        }
//        int size2 = seatDao.selectAllRecords();
//        findView.setSize(size2);
//        List<Seat> list2 = seatDao.selectByTypeAndPage(findView);
//        for (Seat l : list2) {
//            if (l.getSeatState() == 1) {
//                redisCacheManager.sSet("seatAllUsed", l.getSeatId() + "#" + l.getSeatNum());
//            } else if (l.getSeatState() == 0) {
//                redisCacheManager.sSet("seatAllFree", l.getSeatId() + "#" + l.getSeatNum());
//                if (l.getSeatType() == 1) {
//                    redisCacheManager.sSet("seatAllFixedFree", l.getSeatId() + "#" + l.getSeatNum());
//                }
//            }
//        }
//        int size3 = seatTempDao.selectAllRecords();
//        findView.setSize(size3);
//        List<SeatTemp> list3 = seatTempDao.selectByTypeAndPage(findView);
//        for (SeatTemp l : list3) {
//            if (l.getSeatState() == 1) {
//                redisCacheManager.sSet("seatAllUsed", l.getSeatId() + "#" + l.getSeatNum());
//            } else if (l.getSeatState() == 0) {
//                redisCacheManager.sSet("seatAllFree", l.getSeatId() + "#" + l.getSeatNum());
//                redisCacheManager.sSet("seatAllTempFree", l.getSeatId() + "#" + l.getSeatNum());
//            }
//        }
        System.out.println("测试啦");
    }
//
//
    //    缓存所有用户编号，用户名 #分割
    public ResponseJson<Set<Object>> getAllCard() {
        Set<Object> set = redisCacheManager.sGet("cardAll");
        return ResponseJson.createBySuccess(set);
    }

    public void addAllCardItem(String item) {
        redisCacheManager.sSet("cardAll", item);
        System.out.println("执行成功");
    }

    public void removeAllCardItem(String item) {
//        0为删除所有此处只有一个 也可设置为1
        redisCacheManager.setRemove("cardAll", item);
        System.out.println("执行成功");
    }

    public void setTimeEntryCache(String tempId, long timestamp) {
        redisCacheManager.set(tempId,timestamp);
    }

    public long getTimeEntryCache(String tempId) {
        return (long) redisCacheManager.get(tempId);
    }

    public boolean timeEntryCacheIsExists(String tempId) {
        return redisCacheManager.hasKey(tempId);
    }

    public Set<Object> getAllSeat() {
        return redisCacheManager.sGet("seatAll");
    }

    public void addSeatItem(String seatNum) {
        redisCacheManager.sSet("seatAll", seatNum);
    }

    public void removeSeatItem(String seatNum) {
        redisCacheManager.setRemove("seatAll", seatNum);
    }

    public boolean seatIsExists(String seatNum) {
        return redisCacheManager.sHasKey("seatAll",seatNum);
    }

    public Set<Object> getAllSeatFree() {
        return redisCacheManager.sGet("seatAllFree");
    }

    public void addSeatFreeItem(String item) {
        redisCacheManager.sSet("seatAllFree", item);
    }

    public void removeSeatFreeItem(String item) {
        redisCacheManager.setRemove("seatAllFree", item);
    }
//    public ResponseJson<List<FixedView>> getFixedEntry() {
//        Set<Object> set = redisCacheManager.sGet("fixedEntry");
//        List<FixedView> list = new ArrayList<>();
//        for(Object s: set) {
//            Map map = redisCacheManager.hmget((String) s);
//            FixedView fixedView = new FixedView();
//            fixedView.setFixedId((String) map.get("fixedId"));
//            fixedView.setCardId((String) map.get("cardId"));
//            fixedView.setEntryDate((String) map.get("entryDate"));
//            fixedView.setEntryTime((String) map.get("entryTime"));
//            fixedView.setCarNum((String) map.get("carNum"));
//            fixedView.setUserName((String) map.get("userName"));
//            list.add(fixedView);
//        }
//        return ResponseJson.createBySuccess(list);
//    }


//    public void addFixedEntryItem(FixedView fixedView) {
//        Map map = new BeanMap(fixedView);
//        redisCacheManager.sSet("fixedEntry", fixedView.getFixedId());
//        redisCacheManager.hmset(fixedView.getFixedId(),map);
//    }
//
//    public void removeFixedEntryItem(String fixedId) {
//        redisCacheManager.setRemove("fixedEntry",fixedId);
//        redisCacheManager.del(fixedId);
//    }
//
//    public int getFixedEntryItemSize() {
//
//    }

    @PreDestroy
    public void destroy(){
//        Set<Object> set = redisCacheManager.sGet("cardAll");
//        for(Object s: set){
//            redisCacheManager.del((String) s);
//        }
        redisCacheManager.del("cardAll", "seatAll", "seatAllFree");
//        Set<Object> set2 = redisCacheManager.sGet("fixedEntry");
//        for(Object s: set2) {
//            redisCacheManager.del((String) s);
//        }
//        redisCacheManager.del("fixedEntry");
//        redisCacheManager.del();
//        redisCacheManager.expire("cardAll",-1);
//        redisCacheManager.expire("seatAllFree",-1);
//        redisCacheManager.expire("seatAllUsed",-1);
//        redisCacheManager.expire("seatAllFixedFree",-1);
//        redisCacheManager.expire("seatAllTempFree",-1);
    }
//
//
//    //    缓存所有正在使用的车位编号，车位名 #分割
//    public ResponseJson<Set<Object>> getSeatAllUsed() {
//        Set<Object> Set = redisCacheManager.sGet("seatAllUsed");
//        return ResponseJson.createBySuccess(Set);
//    }
//
//    public void addSeatAllUsedItem(String item) {
//        redisCacheManager.sSet("seatAllUsed", item);
//    }
//
//    public void removeSeatAllUsedItem(String item) {
//        redisCacheManager.setRemove("seatAllUsed", item);
//    }
//
//
//    //    缓存所有没有使用的车位编号，车位名 #分割
//    public ResponseJson<Set<Object>> getSeatAllFree() {
//        Set<Object> Set = redisCacheManager.sGet("seatAllFree");
//        return ResponseJson.createBySuccess(Set);
//    }
//
//    public void addSeatAllFreeItem(String item) {
//        redisCacheManager.sSet("seatAllFree", item);
//    }
//
//    public void removeSeatAllFreeItem(String item) {
//        redisCacheManager.setRemove("seatAllFree", item);
//    }
//
//    //    缓存所有可用于出售的固定车位编号，车位名 #分割
//    public ResponseJson<Set<Object>> getSeatAllFixedFree() {
//        Set<Object> Set = redisCacheManager.sGet("seatAllFixedFree");
//        return ResponseJson.createBySuccess(Set);
//    }
//
//    public void addSeatAllFixedFreeItem(String item) {
//        redisCacheManager.sSet("seatAllFixedFree", item);
//    }
//
//    public void removeSeatAllFixedFreeItem(String item) {
//        redisCacheManager.setRemove("seatAllFixedFree", item);
//    }
//
//    //    缓存所有可用于临时停车的车位编号，车位名 #分割
//    public ResponseJson<Set<Object>> getSeatAllTempFree() {
//        Set<Object> Set = redisCacheManager.sGet("SeatAllTempFree");
//        return ResponseJson.createBySuccess(Set);
//    }
//
//    public void addSeatAllTempFreeItem(String item) {
//        redisCacheManager.sSet("seatAllTempFree", item);
//    }
//
//    public void removeSeatAllTempFreeItem(String item) {
//        redisCacheManager.setRemove("seatAllTempFree", item);
//    }
//
////    0是空闲 1是使用
//    public void changeByStateTemp(int state, String item){
//        int beforeState = -1;
//        if(redisCacheManager.sHasKey("seatAllUsed",item)) {
//            beforeState = 1;
//        } else if(redisCacheManager.sHasKey("seatAllFree", item)) {
//            beforeState = 0;
//        }
//        if(state != beforeState){
////            车位从使用变为空闲
//            if(state == 0 && beforeState == 1) {
////                从使用中删除
//                removeSeatAllUsedItem(item);
////                增加到空闲中
//                addSeatAllFreeItem(item);
////                加入可以临时停车的缓存
//                addSeatAllTempFreeItem(item);
////                车位从空闲变为使用
//            } else if(state == 1 && beforeState == 0) {
//                removeSeatAllFreeItem(item);
//                addSeatAllUsedItem(item);
////                从临时停车的缓存中删除
//                removeSeatAllTempFreeItem(item);
////                新增车位
//            } else if(state == -1 && beforeState == 0) {
//                addSeatAllFreeItem(item);
//                addSeatAllTempFreeItem(item);
//            }
//        }
//    }
//
////
//    public void changeByTypeFixed(String item, int type){
//        int beforeType= -1;
//
//    }
//
//    @PreDestroy
//    public void destroy(){
//        redisCacheManager.expire("cardAll",-1);
//        redisCacheManager.expire("seatAllFree",-1);
//        redisCacheManager.expire("seatAllUsed",-1);
//        redisCacheManager.expire("seatAllFixedFree",-1);
//        redisCacheManager.expire("seatAllTempFree",-1);
//    }
}
