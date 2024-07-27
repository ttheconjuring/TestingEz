package bg.softuni.ninjamicroservice.ninja.dtos;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FactDTO {

    @Column(length = 500)
    private String fact;

}
