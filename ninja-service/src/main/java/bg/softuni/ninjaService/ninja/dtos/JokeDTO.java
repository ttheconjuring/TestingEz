package bg.softuni.ninjaService.ninja.dtos;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JokeDTO {

    @Size(max = 500)
    private String joke;

}
