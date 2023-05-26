package com.ltejeda.best_travel.infrastructre.abstract_services;

import com.ltejeda.best_travel.api.models.requests.TicketRequest;
import com.ltejeda.best_travel.api.models.responses.TicketResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface ITicketService extends CrudService<TicketRequest, TicketResponse, UUID>{
    BigDecimal findPrice(Long flyId);
}
