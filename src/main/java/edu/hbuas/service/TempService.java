package edu.hbuas.service;


import edu.hbuas.common.ResponseJson;
import edu.hbuas.pojo.Temp;
import edu.hbuas.vo.FindView;

import java.util.List;

public interface TempService {
    ResponseJson<String> insertTemp(String carNum);
    ResponseJson<String> insertTempOut(String tempId);
    ResponseJson<String> deleteTemp(String tempId);

    ResponseJson<List<Temp>> selectAll(FindView findView);
    ResponseJson<Integer> selectAllRecords();
    ResponseJson<Integer> selectRecords(FindView findView);
}
