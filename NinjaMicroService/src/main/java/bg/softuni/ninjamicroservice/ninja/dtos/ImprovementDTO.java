package bg.softuni.ninjamicroservice.ninja.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImprovementDTO {

    @NotBlank
    private String idea;

    private Boolean approved;

    private Boolean disapproved;

}
