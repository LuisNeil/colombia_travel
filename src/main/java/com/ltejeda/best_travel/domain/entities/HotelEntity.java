package com.ltejeda.best_travel.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "hotel")
public class HotelEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String address;
    private Integer rating;
    private BigDecimal price;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "hotel",
            cascade = CascadeType.ALL
    )
    private Set<ReservationEntity> reservation;
}
