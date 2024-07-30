package bg.softuni.ninjamicroservice.ninja.web.impl;

import bg.softuni.ninjamicroservice.ninja.dtos.*;
import bg.softuni.ninjamicroservice.ninja.service.NinjaService;
import bg.softuni.ninjamicroservice.ninja.web.NinjaController;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @Override
    @PostMapping("/improvements/post")
    public ResponseEntity<?> postImprovement(@Valid @RequestBody ImprovementDTO improvement) {
        try {
            return new ResponseEntity<>(this.ninjaService.processImprovement(improvement), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid request data", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @DeleteMapping("/improvements/delete/{id}")
    public ResponseEntity<?> deleteImprovement(@PathVariable UUID id) {
        try {
            ImprovementDTO improvementDTO = this.ninjaService.deleteImprovement(id);
            if (improvementDTO != null) {
                return new ResponseEntity<>(improvementDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("We couldn't find such improvement idea!", HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid request data", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
