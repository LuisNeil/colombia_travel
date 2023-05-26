package com.ltejeda.best_travel.infrastructre.abstract_services;

import com.ltejeda.best_travel.api.models.responses.HotelResponse;

import java.util.Set;

public interface IHotelService extends CatalogService<HotelResponse>{
    Set<HotelResponse> readByRating(Integer rating);
}
