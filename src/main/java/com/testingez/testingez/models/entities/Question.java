package com.testingez.testingez.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answers;

    @Column(name = "correct_answer", nullable = false)
    private String correctAnswer;

    @Column(nullable = false)
    private Integer points;

    @ManyToOne
    @JoinColumn(name = "test_id", referencedColumnName = "id")
    private Test test;

}
