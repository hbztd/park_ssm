package edu.hbuas.service.impl;

import edu.hbuas.common.ResponseJson;
import edu.hbuas.dao.FixedDao;
import edu.hbuas.dao.FixedViewDao;
import edu.hbuas.pojo.Fixed;
import edu.hbuas.service.FixedService;
import edu.hbuas.util.TimeUtil;
import edu.hbuas.vo.FindView;
import edu.hbuas.vo.FixedView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("FixedService")
public class FixedServiceImpl implements FixedService {
    private final FixedDao fixedDao;
    private final FixedViewDao fixedViewDao;


    @Autowired
    public FixedServiceImpl(FixedDao fixedDao, FixedViewDao fixedViewDao) {
        this.fixedDao = fixedDao;
        this.fixedViewDao = fixedViewDao;
    }

    @Override
    public ResponseJson<String> insertFixed(String cardId) {
        Fixed fixed = new Fixed();
        fixed.setFixedId("F"+TimeUtil.getTimestamp());
        fixed.setCardId(cardId);
        fixed.setEntryDate(TimeUtil.getLocalDate());
        fixed.setEntryTime(TimeUtil.getLocalTime());
        fixed.setFixedStatus(0);
        if(fixedDao.insertSelective(fixed) > 0) {
            return ResponseJson.createBySuccess("进入停车场场成功");
        }
        return ResponseJson.createByError("进入停车场失败");
    }

    @Override
    public ResponseJson<String> insertFixedOut(String fixedId) {
        Fixed fixed = new Fixed();
        fixed.setFixedId(fixedId);
        fixed.setOutDate(TimeUtil.getLocalDate());
        fixed.setOutTime(TimeUtil.getLocalTime());
        if(fixedDao.updateByPrimaryKeySelective(fixed) > 0) {
            return ResponseJson.createBySuccess("退出停车场成功");
        }
        return ResponseJson.createByError("退出停车场失败");

//        redisService.
    }

    @Override
    public ResponseJson<String> deleteFixed(String fixedId) {
        Fixed fixed = new Fixed();
        fixed.setFixedId(fixedId);
        fixed.setFixedStatus(1);
        if(fixedDao.updateByPrimaryKeySelective(fixed) > 0) {
            return ResponseJson.createBySuccess("删除停车记录成功");
        }
        return ResponseJson.createByError("删除停车记录失败");
    }


    @Override
    public ResponseJson<Integer> selectAllRecords() {
        return ResponseJson.createBySuccess(fixedViewDao.selectAllRecords());
    }

    @Override
    public ResponseJson<Integer> selectAllEntryRecords() {
        return ResponseJson.createBySuccess(fixedViewDao.selectAllEntryRecords());
    }

    @Override
    public ResponseJson<Integer> selectRecords(FindView findView) {
        return ResponseJson.createBySuccess(fixedViewDao.selectRecords(findView));
    }

    @Override
    public ResponseJson<Integer> selectEntryRecords(FindView findView) {
        return ResponseJson.createBySuccess(fixedViewDao.selectEntryRecords(findView));
    }

    @Override
    public ResponseJson<List<FixedView>> selectAllEntry(FindView findView) {
        return ResponseJson.createBySuccess(fixedViewDao.selectEntryByTypeAndPage(findView));
    }

    @Override
    public ResponseJson<List<FixedView>> selectAll(FindView findView) {
        return ResponseJson.createBySuccess(fixedViewDao.selectByTypeAndPage(findView));
    }
}
