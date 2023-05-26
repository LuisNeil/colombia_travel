package com.ltejeda.best_travel.infrastructre.abstract_services;

import com.ltejeda.best_travel.api.models.requests.ReservationRequest;
import com.ltejeda.best_travel.api.models.responses.ReservationResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface IReservationService extends CrudService<ReservationRequest, ReservationResponse, UUID>{
    BigDecimal findPrice(Long hotelId);
}
