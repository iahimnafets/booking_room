package com.bookings.controller;


import com.bookings.dto.Response;
import com.bookings.service.MyHotel;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;


@RestController
@RequestMapping( "/hotel" )
@Slf4j
public class BookingController
{

    private final MyHotel myHotel;

    @Autowired
    public BookingController(final MyHotel myHotel )
    {
        this.myHotel = myHotel;
    }

    @Operation( summary =  "Booking a new room in a specific day, ex: Client1, 101, 2023-01-25" )
    @PutMapping( value = "/booking-room" )
    public ResponseEntity<Response> bookingRoom(
            @RequestParam( name = "quest",  required = true ) final String quest,
            @RequestParam( name = "room",  required = true ) final Integer room,
            @RequestParam( name = "Date-String",  required = true ) final String date  // 2023-01-23
            )
    {
        myHotel.bookingRoom( quest, room, date );

        return ResponseEntity.ok(
                Response.builder()
                        .status(HttpStatus.OK)
                        .message( "Reservation successful")
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @Operation( summary =  "All rooms available in this moment" )
    @GetMapping( value = "/available-rooms" )
    public ResponseEntity<Response> availableRoom( @RequestParam( name = "date",  required = true ) final String date )
    {
        return ResponseEntity.ok(
                Response.builder()
                        .status(HttpStatus.OK)
                        .data(Map.of("available-rooms", myHotel.getAvailableRooms(  LocalDate.parse(date) ) ))
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


}
