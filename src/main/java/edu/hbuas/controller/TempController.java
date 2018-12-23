package edu.hbuas.controller;

import edu.hbuas.common.ResponseJson;
import edu.hbuas.pojo.Temp;
import edu.hbuas.service.TempService;
import edu.hbuas.vo.FindView;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/temp")
public class TempController {
    private final TempService tempService;

    @Autowired
    public TempController(TempService tempService) {
        this.tempService = tempService;
    }

    @RequestMapping(value = "entry.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson<String> entry(String carNum) {
        Subject subject = SecurityUtils.getSubject();
        if(subject.hasRole("user")&&!subject.isPermitted("temp:entry")) {
            return ResponseJson.createByErrorNoPer();
        }
        return tempService.insertTemp(carNum);
    }

    @RequestMapping(value = "out.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson<String> out(String tempId) {
        Subject subject = SecurityUtils.getSubject();
        if(subject.hasRole("user")&&!subject.isPermitted("temp:out")) {
            return ResponseJson.createByErrorNoPer();
        }
        return tempService.insertTempOut(tempId);
    }

    @RequestMapping(value = "delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson<String> delete(String tempId) {
        Subject subject = SecurityUtils.getSubject();
        if(subject.hasRole("user")&&!subject.isPermitted("temp:delete")) {
            return ResponseJson.createByErrorNoPer();
        }
        return tempService.deleteTemp(tempId);
    }


    @RequestMapping(value = "find.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson find(@RequestBody FindView findTempView){
        Subject subject = SecurityUtils.getSubject();
        if(subject.hasRole("user")&&!subject.isPermitted("temp:find")) {
            return ResponseJson.createByErrorNoPer();
        }
        System.out.println(findTempView);
        return tempService.selectAll(findTempView);
    }

    @RequestMapping(value = "record.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson record(@RequestBody FindView findTempView){
        System.out.println(findTempView);
        return tempService.selectRecords(findTempView);
    }

    @RequestMapping(value = "record2.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson recordAll(){
        return tempService.selectAllRecords();
    }
}
