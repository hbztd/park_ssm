package edu.hbuas.vo;


import edu.hbuas.pojo.Card;

import java.io.Serializable;

public class CardView implements Serializable {
    private String cardId;

    private String seatId;

    private String seatNum;

    private String userName;

    private String userGender;

    private String userAddr;

    private String carNum;

    private int cardStatus;

    private static final long serialVersionUID = 1L;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }

    public int getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(int cardStatus) {
        this.cardStatus = cardStatus;
    }

    @Override
    public String toString() {
        return "CardView{" +
                "cardId='" + cardId + '\'' +
                ", seatId='" + seatId + '\'' +
                ", seatNum='" + seatNum + '\'' +
                ", userName='" + userName + '\'' +
                ", userGender='" + userGender + '\'' +
                ", userAddr='" + userAddr + '\'' +
                ", carNum='" + carNum + '\'' +
                '}';
    }


}
