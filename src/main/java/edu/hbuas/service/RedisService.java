package edu.hbuas.service;

import edu.hbuas.common.ResponseJson;


import java.util.Set;

public interface RedisService {
   void init();
//
    ResponseJson<Set<Object>> getAllCard();
    void addAllCardItem(String item);
    void removeAllCardItem(String item);

    long getTimeEntryCache(String tempId);
    void setTimeEntryCache(String tempId, long timestamp);
    boolean timeEntryCacheIsExists(String tempId);

    Set<Object> getAllSeat();
    void addSeatItem(String seatNum);
    void removeSeatItem(String seatNum);
    boolean seatIsExists(String seatNum);

    Set<Object> getAllSeatFree();
    void addSeatFreeItem(String item);
    void removeSeatFreeItem(String item);

    void destroy();
//
//    public ResponseJson<Set<Object>> getSeatAllUsed();
//    public void addSeatAllUsedItem(String item);
//    public void removeSeatAllUsedItem(String item);
//
//    public ResponseJson<Set<Object>> getSeatAllFree();
//    public void addSeatAllFreeItem(String item);
//    public void removeSeatAllFreeItem(String item);
//
//    public ResponseJson<Set<Object>> getSeatAllFixedFree();
//    public void addSeatAllFixedFreeItem(String item);
//    public void removeSeatAllFixedFreeItem(String item);
//
//    public ResponseJson<Set<Object>> getSeatAllTempFree();
//    public void addSeatAllTempFreeItem(String item);
//    public void removeSeatAllTempFreeItem(String item);
}
