package com.ltejeda.best_travel.api.controllers;

import com.ltejeda.best_travel.api.models.responses.FlyResponse;
import com.ltejeda.best_travel.infrastructre.abstract_services.IFlyService;
import com.ltejeda.best_travel.utils.SortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "fly")
public class FlyController {

    private final IFlyService flyService;

    @GetMapping
    private ResponseEntity<Page<FlyResponse>> getAll(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestHeader(required = false) SortType sortType){
        if(Objects.isNull(sortType)) sortType = SortType.NONE;
        Page<FlyResponse> response = this.flyService.readAll(page, size, sortType);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "less_price")
    private ResponseEntity<Set<FlyResponse>> getLessPrice(@RequestParam BigDecimal price){
        Set<FlyResponse> response = this.flyService.readLessPrice(price);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "between_price")
    private ResponseEntity<Set<FlyResponse>> getBetweenPrices(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max){
        Set<FlyResponse> response = this.flyService.readBetweenPrices(min, max);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "origin_destiny")
    private ResponseEntity<Set<FlyResponse>> getByOriginDestiny(
            @RequestParam String origin,
            @RequestParam String destiny){
        Set<FlyResponse> response = this.flyService.readByOriginDestiny(origin, destiny);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }
}
