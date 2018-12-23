package edu.hbuas.controller;

import edu.hbuas.common.ResponseJson;
import edu.hbuas.pojo.Card;
import edu.hbuas.service.CardService;
import edu.hbuas.vo.FindView;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;




@Controller
@RequestMapping(value = "/card")
public class CardController {
    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @RequestMapping(value = "add.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson<String> add(@RequestBody Card card) {
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject);
        System.out.println(card);
        if(subject.hasRole("user")&&!subject.isPermitted("card:add")) {
            return ResponseJson.createByErrorNoPer();
        }
        return cardService.insertCard(card);
    }

    @RequestMapping(value = "delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson<String> delete(@RequestBody Card card) {
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject);
        System.out.println(card);
        if(subject.hasRole("user")&&!subject.isPermitted("card:delete")) {
            return ResponseJson.createByErrorNoPer();
        }
        return cardService.deleteCard(card);
    }

    @RequestMapping(value = "update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson<String> update(@RequestBody Card card) {
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject);
        if(subject.hasRole("user")&&!subject.isPermitted("card:update")) {
            return ResponseJson.createByErrorNoPer();
        }
        return cardService.updateCard(card);
    }

    @RequestMapping(value = "find.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson find(@RequestBody FindView findCardView){
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject);
        System.out.println(findCardView);
        if(subject.hasRole("user")&&!subject.isPermitted("card:find")) {
            return ResponseJson.createByErrorNoPer();
        }
        System.out.println(findCardView);
        return cardService.selectByTypeAndPage(findCardView);
    }

    @RequestMapping(value = "getInfo.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson getInfo() {
        return cardService.getSeatFree();
    }

    @RequestMapping(value = "getInfo2.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson getInfo2() {
        return cardService.getAllCard();
    }

    @RequestMapping(value = "getRecordsNum.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson<Integer> getRecordsNum(){
        return cardService.selectAllRecords();
    }

    @RequestMapping(value = "getRecordNum.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson<Integer> getRecordNum(@RequestBody FindView findView){
        return cardService.selectRecords(findView);
    }
}
