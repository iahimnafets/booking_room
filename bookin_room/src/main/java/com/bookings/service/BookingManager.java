package com.bookings.service;

import java.time.LocalDate;
import java.util.List;

public interface BookingManager {

    /**
     * Return true if there is no booking for the given room on the date,
     * otherwise false
     */
    public boolean isRoomAvailable(Integer room, LocalDate date);
    /**
     * Add a booking for the given guest in the given room on the given
     * date. If the room is not available, throw a suitable Exception.
     */
    public void addBooking(String guest, Integer room, LocalDate date);

    /**
     * Return availability rooms for a specific day
     *
     * @param date
     * @return
     */
    public List<Integer> getAvailableRooms(LocalDate date);

}
