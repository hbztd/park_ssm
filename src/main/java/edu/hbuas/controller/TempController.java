package edu.hbuas.controller;

import edu.hbuas.common.ResponseJson;
import edu.hbuas.pojo.Temp;
import edu.hbuas.service.TempService;
import edu.hbuas.vo.FindView;
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
        return tempService.insertTemp(carNum);
    }

    @RequestMapping(value = "out.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson<String> out(String tempId) {
        return tempService.insertTempOut(tempId);
    }

    @RequestMapping(value = "delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson<String> delete(String tempId) {
        return tempService.deleteTemp(tempId);
    }


    @RequestMapping(value = "find.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson find(@RequestBody FindView findTempView){
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
