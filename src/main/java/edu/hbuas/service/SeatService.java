package edu.hbuas.service;

import edu.hbuas.common.ResponseJson;
import edu.hbuas.pojo.Seat;
import edu.hbuas.vo.FindView;


public interface SeatService {
    ResponseJson<String> insertSeat(Seat seat);
    ResponseJson<String> deleteSeat(Seat seat);
    ResponseJson<String> updateSeat(Seat seat);
    ResponseJson selectByTypeAndPage(FindView findView);
    ResponseJson<Integer> selectAllRecords();
//    只执行一次 两种方法性能考虑（修改Bean，增加sql）
    ResponseJson<Integer> selectRecords(FindView findView);
}
// 0当前不可用（被删除） 可临时停车的固定车位1未卖出 已卖出4  3临时停车位