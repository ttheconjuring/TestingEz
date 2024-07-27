package bg.softuni.ninjamicroservice.ninja.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImprovementDTO {

    private String idea;

    private Boolean approved;

    private Boolean disapproved;

}
