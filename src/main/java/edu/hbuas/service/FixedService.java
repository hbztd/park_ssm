package edu.hbuas.service;

import edu.hbuas.common.ResponseJson;
import edu.hbuas.pojo.Fixed;
import edu.hbuas.vo.FindView;
import edu.hbuas.vo.FixedView;

import java.util.List;

public interface FixedService {
    ResponseJson<String> insertFixed(String cardId);
    ResponseJson<String> deleteFixed(String fixedId);
    ResponseJson<String> insertFixedOut(String fixedId);
    ResponseJson<Integer> selectAllRecords();
    ResponseJson<Integer> selectAllEntryRecords();
    ResponseJson<Integer> selectRecords(FindView findView);
    ResponseJson<Integer> selectEntryRecords(FindView findView);
    ResponseJson<List<FixedView>> selectAllEntry(FindView findView);
    ResponseJson<List<FixedView>> selectAll(FindView findView);
}
