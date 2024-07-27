package bg.softuni.ninjamicroservice.ninja.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "improvements")
public class Improvement {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    @Column(nullable = false)
    private String idea;

    @Column(nullable = false)
    private Boolean approved;

    @Column(nullable = false)
    private Boolean disapproved;

}
