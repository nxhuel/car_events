package com.nxhu.eventosDeAutos.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "feedback")
public class FeedBackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedback_id;

    @ManyToOne
    @JoinColumn(name = "user_dni", nullable = false)
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = true)
    private String comment;
}
