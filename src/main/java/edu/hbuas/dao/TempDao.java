package edu.hbuas.dao;

import edu.hbuas.pojo.Temp;
import edu.hbuas.vo.FindView;

import java.util.List;

public interface TempDao {
    int deleteByPrimaryKey(String tempId);

    int insert(Temp record);

    int insertSelective(Temp record);

    Temp selectByPrimaryKey(String tempId);

    int updateByPrimaryKeySelective(Temp record);

    int updateByPrimaryKey(Temp record);

    int deleteByChangeStatus(String tempId);

    List<Temp> selectByTypeAndPage(FindView findSeatView);

    int selectAllRecords();

    int selectRecord(FindView findView);
}