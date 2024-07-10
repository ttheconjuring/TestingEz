package com.testingez.testingez.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "responses")
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "response_text", nullable = false)
    private String responseText;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect;

    @Column(name = "submitten_on", nullable = false)
    private LocalDateTime submittedOn;

    @ManyToOne
    private User user;

    @ManyToOne
    private Question question;

}
