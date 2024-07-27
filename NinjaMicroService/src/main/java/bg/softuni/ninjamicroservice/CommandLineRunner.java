package bg.softuni.ninjamicroservice;

import bg.softuni.ninjamicroservice.ninja.entities.Improvement;
import bg.softuni.ninjamicroservice.ninja.repositories.ImprovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {

    private final ImprovementRepository improvementRepository;

    @Override
    public void run(String... args) throws Exception {

        Improvement improvement1 = new Improvement();
        improvement1.setIdea("wtf");
        improvement1.setApproved(false);
        improvement1.setDisapproved(false);
        Improvement improvement2 = new Improvement();
        improvement2.setIdea("omg");
        improvement2.setApproved(false);
        improvement2.setDisapproved(false);
        Improvement improvement3 = new Improvement();
        improvement3.setIdea("ffs");
        improvement3.setApproved(false);
        improvement3.setDisapproved(false);

        this.improvementRepository.saveAndFlush(improvement1);
        this.improvementRepository.saveAndFlush(improvement2);
        this.improvementRepository.saveAndFlush(improvement3);

    }
}
