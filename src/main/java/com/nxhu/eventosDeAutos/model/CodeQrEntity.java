package com.nxhu.eventosDeAutos.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "codes_qr")
public class CodeQrEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code_id;

    @OneToOne
    @JoinColumn(name = "event_id", nullable = true)
    private EventEntity event_id;

    @Column(nullable = false)
    private String code_qr;
}
