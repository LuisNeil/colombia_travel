package com.ltejeda.best_travel.infrastructre.services;

import com.ltejeda.best_travel.api.models.requests.TicketRequest;
import com.ltejeda.best_travel.api.models.responses.FlyResponse;
import com.ltejeda.best_travel.api.models.responses.TicketResponse;
import com.ltejeda.best_travel.domain.entities.CustomerEntity;
import com.ltejeda.best_travel.domain.entities.FlyEntity;
import com.ltejeda.best_travel.domain.entities.TicketEntity;
import com.ltejeda.best_travel.domain.repositories.CustomerRepository;
import com.ltejeda.best_travel.domain.repositories.FlyRepository;
import com.ltejeda.best_travel.domain.repositories.TicketRepository;
import com.ltejeda.best_travel.infrastructre.abstract_services.ITicketService;
import com.ltejeda.best_travel.utils.BestTravelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Transactional
@Service
@Slf4j
public class TicketService implements ITicketService {

    public static final BigDecimal charger_price_percentage = BigDecimal.valueOf(0.25);
    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;

    public TicketService(FlyRepository flyRepository, CustomerRepository customerRepository, TicketRepository ticketRepository) {
        this.flyRepository = flyRepository;
        this.customerRepository = customerRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public TicketResponse create(TicketRequest request) {
        FlyEntity fly = flyRepository.findById(request.getIdFly()).orElseThrow();
        CustomerEntity customer = customerRepository.findById(request.getIdClient()).orElseThrow();
        TicketEntity ticketToPersist = TicketEntity.builder().id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .price(fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage)))
                .purchaseDate(LocalDate.now())
                .arrivalDate(BestTravelUtil.getRandomLater())
                .departureDate(BestTravelUtil.getRandomSoon())
                .build();
        TicketEntity ticketPersisted = this.ticketRepository.save(ticketToPersist);
        log.info("Ticket saved with id: {}", ticketPersisted.getId());
        return this.entityToResponse(ticketPersisted);
    }

    @Override
    public TicketResponse read(UUID id) {
        TicketEntity ticketFromDB = this.ticketRepository.findById(id).orElseThrow();
        return this.entityToResponse(ticketFromDB);
    }

    @Override
    public TicketResponse update(TicketRequest request, UUID id) {
        TicketEntity ticketToUpdate = ticketRepository.findById(id).orElseThrow();
        FlyEntity fly = flyRepository.findById(request.getIdFly()).orElseThrow();
        ticketToUpdate.setFly(fly);
        ticketToUpdate.setPrice(fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage)));
        ticketToUpdate.setDepartureDate(BestTravelUtil.getRandomSoon());
        ticketToUpdate.setArrivalDate(BestTravelUtil.getRandomLater());
        TicketEntity ticketUpdated = this.ticketRepository.save(ticketToUpdate);
        log.info("Ticket updated with id{}",ticketUpdated.getId());
        return this.entityToResponse(ticketUpdated);
    }

    @Override
    public void delete(UUID id) {
        TicketEntity ticketToDelete = this.ticketRepository.findById(id).orElseThrow();
        this.ticketRepository.delete(ticketToDelete);

    }

    private TicketResponse entityToResponse(TicketEntity entity){
        var response = new TicketResponse();
        BeanUtils.copyProperties(entity, response);
        var flyResponse = new FlyResponse();
        BeanUtils.copyProperties(entity.getFly(), flyResponse);
        response.setFly(flyResponse);
        return response;
    }

    @Override
    public BigDecimal findPrice(Long flyId) {
        FlyEntity fly = this.flyRepository.findById(flyId).orElseThrow();
        return fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage));
    }
}
