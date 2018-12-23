package edu.hbuas.controller;

import edu.hbuas.common.ResponseJson;
import edu.hbuas.pojo.Seat;
import edu.hbuas.service.SeatService;
import edu.hbuas.util.RedisCacheManager;
import edu.hbuas.vo.FindView;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/seat")
public class SeatController {
    private final SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @RequestMapping(value = "add.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson<String> add(@RequestBody Seat seat) {
        Subject subject = SecurityUtils.getSubject();
        if(subject.hasRole("user")&&!subject.isPermitted("seat:add")) {
            return ResponseJson.createByErrorNoPer();
        }
        System.out.println(seat);
        return seatService.insertSeat(seat);
    }

    @RequestMapping(value = "delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson<String> delete(@RequestBody Seat seat) {
        Subject subject = SecurityUtils.getSubject();
        if(subject.hasRole("user")&&!subject.isPermitted("seat:delete")) {
            return ResponseJson.createByErrorNoPer();
        }
        System.out.println(seat.getSeatId());
        return seatService.deleteSeat(seat);
    }

    @RequestMapping(value = "update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson<String> update(@RequestBody Seat seat) {
        Subject subject = SecurityUtils.getSubject();
        if(subject.hasRole("user")&&!subject.isPermitted("seat:update")) {
            return ResponseJson.createByErrorNoPer();
        }
        return seatService.updateSeat(seat);
    }

    @RequestMapping(value = "find.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson find(@RequestBody FindView findSeatView){
        Subject subject = SecurityUtils.getSubject();
        if(subject.hasRole("user")&&!subject.isPermitted("seat:find")) {
            return ResponseJson.createByErrorNoPer();
        }
        System.out.println(findSeatView);
        return seatService.selectByTypeAndPage(findSeatView);
    }

    @RequestMapping(value = "getRecordsNum.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson<Integer> getRecordsNum(){
        return seatService.selectAllRecords();
    }

    @RequestMapping(value = "getRecordNum.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson<Integer> getRecordNum(@RequestBody FindView findView){
        return seatService.selectRecords(findView);
    }
}
