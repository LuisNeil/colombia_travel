package com.ltejeda.best_travel.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourHotelRequest implements Serializable {

    private Long id;
    private Integer totalDays;
}
