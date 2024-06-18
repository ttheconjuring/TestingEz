package com.testingez.testingez.models.entities;

import com.testingez.testingez.models.enums.TestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tests")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(name = "response_time", nullable = false)
    private Integer responseTime;

    @Column(name = "passing_score", nullable = false)
    private Integer passingScore;

    @Column(name = "questions_count", nullable = false)
    private Integer questionsCount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TestStatus status;

    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;

    @Column(name = "date_updated", nullable = false)
    private LocalDateTime dateUpdated;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private User creator;

    @OneToMany(mappedBy = "test")
    private List<Question> questions;

}
