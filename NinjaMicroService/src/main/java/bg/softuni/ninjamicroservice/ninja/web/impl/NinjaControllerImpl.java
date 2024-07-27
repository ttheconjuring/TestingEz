package bg.softuni.ninjamicroservice.ninja.web.impl;

import bg.softuni.ninjamicroservice.ninja.dtos.*;
import bg.softuni.ninjamicroservice.ninja.service.NinjaService;
import bg.softuni.ninjamicroservice.ninja.web.NinjaController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/ninja/api")
public class NinjaControllerImpl implements NinjaController {

    private final NinjaService ninjaService;

    @Override
    @GetMapping("/facts")
    public ResponseEntity<FactDTO[]> getFacts() {
        return ResponseEntity.ok(this.ninjaService.getFacts());
    }

    @Override
    @GetMapping("/jokes")
    public ResponseEntity<JokeDTO[]> getJokes() {
        return ResponseEntity.ok(this.ninjaService.getJokes());
    }

    @Override
    @GetMapping("/trivia")
    public ResponseEntity<TriviaDTO[]> getTrivia() {
        return ResponseEntity.ok(this.ninjaService.getTrivia());
    }

    @Override
    @GetMapping("/quotes")
    public ResponseEntity<QuoteDTO[]> getQuotes() {
        return ResponseEntity.ok(this.ninjaService.getQuotes());
    }

    @Override
    @GetMapping("/improvements")
    public ResponseEntity<ImprovementDTO[]> getImprovements() {
        return ResponseEntity.ok(this.ninjaService.getImprovements());
    }

}
