package edu.hbuas.service;

import edu.hbuas.common.ResponseJson;
import edu.hbuas.pojo.Card;

import edu.hbuas.vo.FindView;

import java.util.List;
import java.util.Set;

public interface CardService {
    ResponseJson<String> insertCard(Card card);
    ResponseJson<String> deleteCard(Card card);
    ResponseJson<String> updateCard(Card card);
    ResponseJson<Set<Object>> getSeatFree();
    ResponseJson<Set<Object>> getAllCard();
    ResponseJson selectByTypeAndPage(FindView findView);
    ResponseJson<Integer> selectAllRecords();
    ResponseJson<Integer> selectRecords(FindView findView);
}
