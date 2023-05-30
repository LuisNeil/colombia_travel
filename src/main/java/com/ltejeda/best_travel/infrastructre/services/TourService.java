package com.ltejeda.best_travel.infrastructre.services;

import com.ltejeda.best_travel.api.models.requests.TourRequest;
import com.ltejeda.best_travel.api.models.responses.TourResponse;
import com.ltejeda.best_travel.domain.entities.*;
import com.ltejeda.best_travel.domain.repositories.CustomerRepository;
import com.ltejeda.best_travel.domain.repositories.FlyRepository;
import com.ltejeda.best_travel.domain.repositories.HotelRepository;
import com.ltejeda.best_travel.domain.repositories.TourRepository;
import com.ltejeda.best_travel.infrastructre.abstract_services.ITourService;
import com.ltejeda.best_travel.infrastructre.helpers.TourHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
public class TourService implements ITourService {

    private final TourRepository tourRepository;
    private final FlyRepository flyRepository;
    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final TourHelper tourHelper;
    @Override
    public void removeTicket(Long tourId,UUID ticketId) {
        TourEntity tourUpdate = this.tourRepository.findById(tourId).orElseThrow();
        tourUpdate.removeTicket(ticketId);
        this.tourRepository.save(tourUpdate);
    }

    @Override
    public UUID addTicket(Long tourId,Long flyId) {
        TourEntity tourUpdate = this.tourRepository.findById(tourId).orElseThrow();
        FlyEntity fly = this.flyRepository.findById(flyId).orElseThrow();
        TicketEntity ticket = this.tourHelper.createTicket(fly, tourUpdate.getCustomer());
        tourUpdate.addTicket(ticket);
        this.tourRepository.save(tourUpdate);
        return ticket.getId();
    }

    @Override
    public void removeReservation(Long tourId, UUID reservationId) {

    }

    @Override
    public UUID addReservation(Long tourId,Long reservationId) {
        return null;
    }

    @Override
    public TourResponse create(TourRequest request) {
        CustomerEntity customer = customerRepository.findById(request.getCustomerId()).orElseThrow();
        Set<FlyEntity> flights = new HashSet<>();
        request.getFlights().forEach(fly -> flights.add(this.flyRepository.findById(fly.getId()).orElseThrow()));
        Map<HotelEntity, Integer> hotels = new HashMap<>();
        request.getHotels().forEach(hotel -> hotels.put(this.hotelRepository.findById(hotel.getId()).orElseThrow(),hotel.getTotalDays()));
        TourEntity tourToSave = TourEntity.builder()
                .tickets(this.tourHelper.createTicket(flights, customer))
                .reservations(this.tourHelper.createReservation(hotels, customer))
                .customer(customer)
                .build();
        TourEntity tourSaved = this.tourRepository.save(tourToSave);
        return TourResponse.builder()
                .reservationIds(tourSaved.getReservations().stream().map(ReservationEntity::getId).collect(Collectors.toSet()))
                .ticketIds(tourSaved.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet()))
                .id(tourSaved.getId())
                .build();
    }

    @Override
    public TourResponse read(Long id) {
        TourEntity tourFromDb = this.tourRepository.findById(id).orElseThrow();
        return TourResponse.builder()
                .reservationIds(tourFromDb.getReservations().stream().map(ReservationEntity::getId).collect(Collectors.toSet()))
                .ticketIds(tourFromDb.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet()))
                .id(tourFromDb.getId())
                .build();
    }

    @Override
    public void delete(Long id) {
        this.tourRepository.delete(this.tourRepository.findById(id).orElseThrow());
    }
}
