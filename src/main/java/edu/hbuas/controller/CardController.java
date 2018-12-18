package edu.hbuas.controller;

import edu.hbuas.common.ResponseJson;
import edu.hbuas.pojo.Card;
import edu.hbuas.service.CardService;
import edu.hbuas.service.RedisService;
import edu.hbuas.vo.FindView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;



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
        System.out.println(card);
        return cardService.insertCard(card);
    }

    @RequestMapping(value = "delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson<String> delete(@RequestBody Card card) {
        return cardService.deleteCard(card);
    }

    @RequestMapping(value = "update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson<String> update(@RequestBody Card card) {
        return cardService.updateCard(card);
    }

    @RequestMapping(value = "find.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson find(@RequestBody FindView findCardView){
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
