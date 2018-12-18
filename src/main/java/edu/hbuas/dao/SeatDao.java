package edu.hbuas.dao;

import edu.hbuas.pojo.Seat;
import edu.hbuas.vo.FindView;

import java.util.List;

public interface SeatDao {
    int deleteByPrimaryKey(String seatId);

    int insert(Seat record);

    int insertSelective(Seat record);

    Seat selectByPrimaryKey(String seatId);

    int updateByPrimaryKeySelective(Seat record);

    int updateByPrimaryKey(Seat record);

    List<Seat> selectByTypeAndPage(FindView findSeatView);

    int selectAllRecords();

    int selectRecords(FindView findView);


}