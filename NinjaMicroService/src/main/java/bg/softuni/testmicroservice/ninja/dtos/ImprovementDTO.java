package bg.softuni.testmicroservice.ninja.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImprovementDTO {

    private String idea;

    private Integer likes;

    private Integer dislikes;

}
