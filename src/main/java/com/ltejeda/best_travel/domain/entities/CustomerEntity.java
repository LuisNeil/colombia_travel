package com.ltejeda.best_travel.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerEntity implements Serializable {

    @Id
    private String dni;
    @Column(length = 50)
    private String fullName;
    @Column(length = 20)
    private String creditCard;
    @Column(length = 12)
    private String phoneNumber;
    private Integer totalFlights;
    private Integer totalLodgings;
    private Integer totalTours;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "customer",
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private Set<TicketEntity> tickets;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "customer",
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private Set<ReservationEntity> reservationEntitySet;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "customer",
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private Set<TourEntity> tours;
}
