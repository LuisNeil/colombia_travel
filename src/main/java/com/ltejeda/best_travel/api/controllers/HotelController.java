package com.ltejeda.best_travel.api.controllers;

import com.ltejeda.best_travel.api.models.responses.HotelResponse;
import com.ltejeda.best_travel.infrastructre.abstract_services.IHotelService;
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
@RequestMapping(path = "hotel")
public class HotelController {

    private final IHotelService hotelService;

    @GetMapping
    private ResponseEntity<Page<HotelResponse>> getAll(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestHeader(required = false)SortType sortType
            ){
        if(Objects.isNull(sortType)) sortType = SortType.NONE;
        Page<HotelResponse> response = this.hotelService.readAll(page, size, sortType);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "less_price")
    private ResponseEntity<Set<HotelResponse>> getLessPrice(@RequestParam BigDecimal price){
        Set<HotelResponse> response = this.hotelService.readLessPrice(price);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "between_price")
    private ResponseEntity<Set<HotelResponse>> getBetweenPrice(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max){
        Set<HotelResponse> response = this.hotelService.readBetweenPrices(min, max);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "rating")
    private ResponseEntity<Set<HotelResponse>> getByRating(@RequestParam Integer rating){
        if(rating > 4) rating = 4;
        if(rating < 1) rating = 1;
        Set<HotelResponse> response = this.hotelService.readByRating(rating);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

}
