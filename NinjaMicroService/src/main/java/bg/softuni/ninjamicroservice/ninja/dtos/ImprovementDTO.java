package bg.softuni.ninjamicroservice.ninja.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImprovementDTO {

    @NotBlank
    private String idea;

    @NotNull
    private Boolean approved;

    @NotNull
    private Boolean disapproved;

}
