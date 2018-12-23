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
    ResponseJson<Integer> selectRecords(FindView findView);
}