package com.ltejeda.best_travel.infrastructre.abstract_services;

import com.ltejeda.best_travel.api.models.requests.TourRequest;
import com.ltejeda.best_travel.api.models.responses.TourResponse;

import java.util.UUID;

public interface ITourService extends SimpleCrudService<TourRequest, TourResponse, Long> {
    void removeTicket(Long tourId, UUID ticketId);
    UUID addTicket(Long tourId,Long flyId);
    void removeReservation(Long tourId, UUID reservationId);
    UUID addReservation(Long tourId, Long reservationId);


}
