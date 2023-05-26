package com.ltejeda.best_travel.domain.repositories;

import com.ltejeda.best_travel.domain.entities.ReservationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository extends CrudRepository<ReservationEntity, UUID> {

}
