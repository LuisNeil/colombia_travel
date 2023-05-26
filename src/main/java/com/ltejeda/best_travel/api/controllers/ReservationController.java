package com.ltejeda.best_travel.api.controllers;

import com.ltejeda.best_travel.api.models.requests.ReservationRequest;
import com.ltejeda.best_travel.api.models.responses.ReservationResponse;
import com.ltejeda.best_travel.infrastructre.abstract_services.IReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "reservation")
public class ReservationController {

    private final IReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> post(@RequestBody ReservationRequest request){
        return ResponseEntity.ok(reservationService.create(request));
    }

    @GetMapping("{id}")
    public ResponseEntity<ReservationResponse> get(@PathVariable UUID id){
        return ResponseEntity.ok(this.reservationService.read(id));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<ReservationResponse> put(@PathVariable UUID id, @RequestBody ReservationRequest request){
        return ResponseEntity.ok(this.reservationService.update(request,id));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        this.reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>> getReservationPrice(@RequestParam Long reservationId){
        return ResponseEntity.ok(Collections.singletonMap("ticketPrice", this.reservationService.findPrice(reservationId)));
    }
}
