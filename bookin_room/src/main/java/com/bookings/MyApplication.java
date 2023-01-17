package com.bookings;

import com.bookings.service.MyHotel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@Slf4j
public class MyApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(MyApplication.class, args);
	}


	@Bean
	CommandLineRunner run(MyHotel myHotel) {

		return args -> {
			log.info("Added all rooms for this hotel ");
			myHotel.addRooms(List.of(new Integer[]{100, 101, 102, 103, 104, 105, 106 }));
		};
	}

}
