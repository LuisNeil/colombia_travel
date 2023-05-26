package com.ltejeda.best_travel.infrastructre.abstract_services;

import com.ltejeda.best_travel.api.models.responses.FlyResponse;

import java.util.Set;

public interface IFlyService extends CatalogService<FlyResponse>{
    Set<FlyResponse> readByOriginDestiny(String origin, String destiny);
}
