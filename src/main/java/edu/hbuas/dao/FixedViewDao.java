package edu.hbuas.dao;

import edu.hbuas.pojo.Seat;
import edu.hbuas.vo.FindView;
import edu.hbuas.vo.FixedView;

import java.util.List;

public interface FixedViewDao {
    int insert(FixedView record);

    int insertSelective(FixedView record);

    List<FixedView> selectByTypeAndPage(FindView findFixedView);

    int selectRecords(FindView findView);

    int selectAllRecords();

    List<FixedView> selectEntryByTypeAndPage(FindView findFixedView);

    int selectEntryRecords(FindView findView);

    int selectAllEntryRecords();
}