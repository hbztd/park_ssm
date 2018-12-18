package edu.hbuas.controller;

import edu.hbuas.common.ResponseJson;
import edu.hbuas.pojo.Fixed;
import edu.hbuas.service.FixedService;
import edu.hbuas.vo.FindView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/fixed")
public class FixedController {
    private final FixedService fixedService;

    @Autowired
    public FixedController(FixedService fixedService) {
        this.fixedService = fixedService;
    }

    @RequestMapping(value = "entry.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson<String> entry(String cardId) {
        return fixedService.insertFixed(cardId);
    }

    @RequestMapping(value = "out.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson<String> out(String fixedId) {
        return fixedService.insertFixedOut(fixedId);
    }

    @RequestMapping(value = "delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson<String> delete(String fixedId) {
        return fixedService.deleteFixed(fixedId);
    }


    @RequestMapping(value = "find.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson find(@RequestBody FindView findFixedView){
        System.out.println(findFixedView);
        return fixedService.selectAll(findFixedView);
    }

    @RequestMapping(value = "findEntry.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson findEntry(@RequestBody FindView findFixedView){
        System.out.println(findFixedView);
        return fixedService.selectAllEntry(findFixedView);
    }

    @RequestMapping(value = "record.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson record(@RequestBody FindView findFixedView){
        System.out.println(findFixedView);
        return fixedService.selectRecords(findFixedView);
    }

    @RequestMapping(value = "record2.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson recordAll(){
        return fixedService.selectAllRecords();
    }

    @RequestMapping(value = "record3.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson recordEntry(@RequestBody FindView findFixedView){
        return fixedService.selectEntryRecords(findFixedView);
    }

    @RequestMapping(value = "record4.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson recordAllEntry(){
        return fixedService.selectAllEntryRecords();
    }
}
