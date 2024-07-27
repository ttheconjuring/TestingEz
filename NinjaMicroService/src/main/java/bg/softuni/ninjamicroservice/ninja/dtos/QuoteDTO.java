package bg.softuni.ninjamicroservice.ninja.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuoteDTO {

    @NotNull(message = "Quote should not be null")
    @Size(max = 500, message = "Quote length should be up to 500 symbols.")
    private String quote;

    @NotNull(message = "Author should not be null.")
    @Size(max = 500, message = "Author name should be up to 500 symbols.")
    private String author;

}
