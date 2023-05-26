package com.ltejeda.best_travel;

import com.ltejeda.best_travel.domain.entities.ReservationEntity;
import com.ltejeda.best_travel.domain.entities.TicketEntity;
import com.ltejeda.best_travel.domain.entities.TourEntity;
import com.ltejeda.best_travel.domain.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
@Slf4j
public class BestTravelApplication {

    private final CustomerRepository customerRepository;
    private final FlyRepository flyRepository;
    private final HotelRepository hotelRepository;
    private final ReservationRepository reservationRepository;
    private final TicketRepository ticketRepository;
    private final TourRepository tourRepository;

    public BestTravelApplication(CustomerRepository customerRepository,
                                 FlyRepository flyRepository,
                                 HotelRepository hotelRepository,
                                 ReservationRepository reservationRepository,
                                 TicketRepository ticketRepository,
                                 TourRepository tourRepository) {
        this.customerRepository = customerRepository;
        this.flyRepository = flyRepository;
        this.hotelRepository = hotelRepository;
        this.reservationRepository = reservationRepository;
        this.ticketRepository = ticketRepository;
        this.tourRepository = tourRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BestTravelApplication.class, args);
    }

}

