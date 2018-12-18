package edu.hbuas.dao;

import edu.hbuas.pojo.Card;
import edu.hbuas.vo.CardView;
import edu.hbuas.vo.FindView;

import java.util.List;

public interface CardDao {
    int deleteByPrimaryKey(String cardId);

    int insert(Card record);

    int insertSelective(Card record);

    Card selectByPrimaryKey(String cardId);

    int updateByPrimaryKeySelective(Card record);

    int updateByPrimaryKey(Card record);

    List<CardView> selectByTypeAndPage(FindView findCardView);

    int selectAllRecords();

    int selectRecords(FindView findView);

    int deleteByChangeStatus(String cardId);
}