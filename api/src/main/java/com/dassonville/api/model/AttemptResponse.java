package com.dassonville.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "attempts_responses")
@Getter
@Setter
public class AttemptResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(insertable = false, updatable = false)
    private LocalDateTime response_date;

    @ManyToOne
    @JoinColumn(name = "id_attempt", referencedColumnName = "id", nullable = false)
    private Attempt attempt;

    @ManyToOne
    @JoinColumn(name = "id_question", referencedColumnName = "id", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "id_answer", referencedColumnName = "id")
    private Answer answer;
}
