package edu.hbuas.dao;

import edu.hbuas.pojo.Fixed;
import edu.hbuas.vo.FindView;

import java.util.List;

public interface FixedDao {
    int deleteByPrimaryKey(String fixedId);

    int insert(Fixed record);

    int insertSelective(Fixed record);

    Fixed selectByPrimaryKey(String fixedId);

    int updateByPrimaryKeySelective(Fixed record);

    int updateByPrimaryKey(Fixed record);

    List<Fixed> selectByTypeAndPage(FindView findCardView);

    int selectAllRecords();
}