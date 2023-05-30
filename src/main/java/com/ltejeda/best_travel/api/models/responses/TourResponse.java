package com.ltejeda.best_travel.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourResponse implements Serializable {
     private Long id;
     private Set<UUID> ticketIds;
     private Set<UUID> reservationIds;

}