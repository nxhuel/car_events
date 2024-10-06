package com.nxhu.eventosDeAutos.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long event_id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String category;

    private BigDecimal price;
    private String image;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity creator;
}
