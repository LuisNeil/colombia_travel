package com.ltejeda.best_travel.infrastructre.helpers;

import com.ltejeda.best_travel.domain.entities.*;
import com.ltejeda.best_travel.domain.repositories.ReservationRepository;
import com.ltejeda.best_travel.domain.repositories.TicketRepository;
import com.ltejeda.best_travel.infrastructre.services.ReservationService;
import com.ltejeda.best_travel.infrastructre.services.TicketService;
import com.ltejeda.best_travel.utils.BestTravelUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.ltejeda.best_travel.infrastructre.services.ReservationService.charges_price_percentage;
import static com.ltejeda.best_travel.infrastructre.services.TicketService.charger_price_percentage;

@Transactional
@Component
@AllArgsConstructor
public class TourHelper {
    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;

    public Set<TicketEntity> createTicket(Set<FlyEntity> flights, CustomerEntity customer){
        Set<TicketEntity> response = new HashSet<>(flights.size());
        flights.forEach(fly -> {
            TicketEntity ticketToPersist = TicketEntity.builder().id(UUID.randomUUID())
                    .fly(fly)
                    .customer(customer)
                    .price(fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage)))
                    .purchaseDate(LocalDate.now())
                    .arrivalDate(BestTravelUtil.getRandomLater())
                    .departureDate(BestTravelUtil.getRandomSoon())
                    .build();
            response.add(this.ticketRepository.save(ticketToPersist));
        });
        return response;
    }

    public Set<ReservationEntity> createReservation(Map<HotelEntity, Integer> hotels, CustomerEntity customer){
        Set<ReservationEntity> response = new HashSet<>(hotels.size());
        hotels.forEach((hotel, totalDays) ->{
            ReservationEntity reservationToPersist = ReservationEntity.builder()
                    .id(UUID.randomUUID())
                    .hotel(hotel)
                    .customer(customer)
                    .totalDays(totalDays)
                    .dateTimeReservation(LocalDateTime.now())
                    .dateStart(LocalDate.now())
                    .dateEnd(LocalDate.now().plusDays(totalDays))
                    .price(hotel.getPrice().add(hotel.getPrice().multiply(charges_price_percentage)))
                    .build();
            response.add(this.reservationRepository.save(reservationToPersist));
        });
        return response;
    }

    public TicketEntity createTicket(FlyEntity fly, CustomerEntity customer){
        TicketEntity ticketToPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .purchaseDate(LocalDate.now())
                .arrivalDate(BestTravelUtil.getRandomLater())
                .departureDate(BestTravelUtil.getRandomSoon())
                .price(fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage)))
                .build();
        return this.ticketRepository.save(ticketToPersist);
    }

    public ReservationEntity createReservation(HotelEntity hotel, CustomerEntity customer, Integer totalDays){
        ReservationEntity reservationToPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .hotel(hotel)
                .customer(customer)
                .totalDays(totalDays)
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(totalDays))
                .price(hotel.getPrice().add(hotel.getPrice().multiply(charges_price_percentage)))
                .build();
        return this.reservationRepository.save(reservationToPersist);
    }

}
