package com.bookings.service;


import com.bookings.exception.ApiRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;


@Service
@Slf4j
public class MyHotel implements BookingManager {

    private HashMap<Integer, HashMap<LocalDate,String>> roomsHotel = new HashMap();

    /**
     * Adding synchronized I'm sure that each thread waits its turn to insert its reservation
     * @param quest
     * @param room
     * @param dateStr
     * @return
     */
    public synchronized void bookingRoom(String quest, Integer room, String dateStr ) {
        log.info("bookingRoom-RUN  quest: {}, room: {}, dateStr: {}", quest, room,  dateStr);
        LocalDate date = LocalDate.parse(dateStr);
        if(isRoomAvailable(room, date)) {
            addBooking(quest, room, date);
        }else{
            throw new ApiRequestException(" Room not available ");
        }
    }

    @Override
    public synchronized boolean isRoomAvailable(Integer room, LocalDate date) {
        log.info("isRoomAvailable-RUN  room: {}, date: {}", room, date );
        boolean result = false;
        if(!roomsHotel.containsKey(room)) {
            throw new ApiRequestException(" Room not present in this hotel ");
        }
        if( roomsHotel.get(room)==null ){
            return true;
        }
        if( roomsHotel.get(room).get(date) == null){
            return true;
        }
        return result;
    }

    @Override
    public synchronized void addBooking(String guest, Integer room, LocalDate date) {
        log.info("addBooking-RUN  guest: {}, room: {}, date: {}", guest, room, date );

        if(roomsHotel.get(room) == null ){
            HashMap mapDaysForRoom = new HashMap<>();
            mapDaysForRoom.put( date, guest);
            roomsHotel.put(room, mapDaysForRoom);
        }else{
            roomsHotel.get(room).put(date, guest);
        }
    }

    @Override
    public synchronized List<Integer> getAvailableRooms(LocalDate localDate ){

        log.info("getAvailableRooms-RUN  date: {} ", localDate );
        List<Integer> result = new ArrayList<>();

        for (Map.Entry<Integer, HashMap<LocalDate,String> > room : roomsHotel.entrySet()) {
            if(room.getValue() == null){
                result.add(room.getKey());
            }else {
                for (Map.Entry<LocalDate, String> date : room.getValue().entrySet()) {
                    if (!date.getKey().toString().equals(localDate.toString())) {
                        result.add(room.getKey());
                    }
                }
            }
        }
        return result;
    }


    public synchronized void addRooms(List<Integer> listRooms ) {
        log.info("addRooms-RUN  listRooms: {} ", listRooms );
        for(Integer room : listRooms ) {
           roomsHotel.put(room, null);
       }
    }
}
