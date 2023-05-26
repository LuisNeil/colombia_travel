package com.ltejeda.best_travel.infrastructre.services;

import com.ltejeda.best_travel.api.models.responses.HotelResponse;
import com.ltejeda.best_travel.infrastructre.abstract_services.IHotelService;
import com.ltejeda.best_travel.utils.SortType;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Set;

public class HotelService implements IHotelService {
    @Override
    public Page<HotelResponse> readAll(Integer page, Integer size, SortType sortType) {
        return null;
    }

    @Override
    public Set<HotelResponse> readLessPrice(BigDecimal price) {
        return null;
    }

    @Override
    public Set<HotelResponse> readBetweenPrices(BigDecimal min, BigDecimal max) {
        return null;
    }

    @Override
    public Set<HotelResponse> readGreaterThan(Integer rating) {
        return null;
    }
}
