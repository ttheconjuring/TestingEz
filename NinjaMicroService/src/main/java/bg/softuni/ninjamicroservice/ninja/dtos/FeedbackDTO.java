package bg.softuni.ninjamicroservice.ninja.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class FeedbackDTO {

    private UUID id;

    @NotBlank
    private String idea;

    private Boolean approved;

    private Boolean disapproved;

    @Override
    public String toString() {
        return String.format("{\"id\":\"%s\",\"idea\":\"%s\",\"approved\":%b,\"disapproved\":%b}",
                id, idea, approved, disapproved);
    }
}
