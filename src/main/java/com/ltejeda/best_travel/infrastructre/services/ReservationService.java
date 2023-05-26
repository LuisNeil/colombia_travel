package com.ltejeda.best_travel.infrastructre.services;

import com.ltejeda.best_travel.api.models.requests.ReservationRequest;
import com.ltejeda.best_travel.api.models.responses.HotelResponse;
import com.ltejeda.best_travel.api.models.responses.ReservationResponse;
import com.ltejeda.best_travel.domain.entities.CustomerEntity;
import com.ltejeda.best_travel.domain.entities.FlyEntity;
import com.ltejeda.best_travel.domain.entities.HotelEntity;
import com.ltejeda.best_travel.domain.entities.ReservationEntity;
import com.ltejeda.best_travel.domain.repositories.CustomerRepository;
import com.ltejeda.best_travel.domain.repositories.HotelRepository;
import com.ltejeda.best_travel.domain.repositories.ReservationRepository;
import com.ltejeda.best_travel.infrastructre.abstract_services.IReservationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class ReservationService implements IReservationService {

    private static final BigDecimal charges_price_percentage = BigDecimal.valueOf(0.20);
    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
    @Override
    public ReservationResponse create(ReservationRequest request) {
        HotelEntity hotel = this.hotelRepository.findById(request.getIdHotel()).orElseThrow();
        CustomerEntity customer = this.customerRepository.findById(request.getIdClient()).orElseThrow();
        ReservationEntity reservationToPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .hotel(hotel)
                .customer(customer)
                .totalDays(request.getTotalDays())
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(request.getTotalDays()))
                .price(hotel.getPrice().add(hotel.getPrice().multiply(charges_price_percentage)))
                .build();
        ReservationEntity reservationPersisted = reservationRepository.save(reservationToPersist);

        return this.entityToResponse(reservationPersisted);
    }

    @Override
    public ReservationResponse read(UUID id) {
        ReservationEntity reservationFromDB = this.reservationRepository.findById(id).orElseThrow();
        return this.entityToResponse(reservationFromDB);
    }

    @Override
    public ReservationResponse update(ReservationRequest request, UUID id) {
        HotelEntity hotel = hotelRepository.findById(request.getIdHotel()).orElseThrow();
        ReservationEntity reservationToUpdate = this.reservationRepository.findById(id).orElseThrow();
        reservationToUpdate.setHotel(hotel);
        reservationToUpdate.setTotalDays(request.getTotalDays());
        reservationToUpdate.setDateTimeReservation(LocalDateTime.now());
        reservationToUpdate.setDateStart(LocalDate.now());
        reservationToUpdate.setDateEnd(LocalDate.now().plusDays(request.getTotalDays()));
        reservationToUpdate.setPrice(hotel.getPrice().add(hotel.getPrice().multiply(charges_price_percentage)));
        ReservationEntity reservationSaved = this.reservationRepository.save(reservationToUpdate);
        log.info("Reservation updated with id {}", reservationToUpdate.getId());
        return this.entityToResponse(reservationSaved);
    }

    @Override
    public void delete(UUID id) {
        ReservationEntity reservationToDelete = this.reservationRepository.findById(id).orElseThrow();
        this.reservationRepository.delete(reservationToDelete);
    }

    private ReservationResponse entityToResponse(ReservationEntity entity){
        ReservationResponse response = new ReservationResponse();
        BeanUtils.copyProperties(entity,response);
        HotelResponse hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(entity.getHotel(), hotelResponse);
        response.setHotel(hotelResponse);
        return response;
    }

    @Override
    public BigDecimal findPrice(Long hotelId) {
        HotelEntity hotel = this.hotelRepository.findById(hotelId).orElseThrow();
        return hotel.getPrice().add(hotel.getPrice().multiply(charges_price_percentage));
    }
}
