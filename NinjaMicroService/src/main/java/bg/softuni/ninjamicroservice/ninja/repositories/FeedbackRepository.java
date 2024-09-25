package bg.softuni.ninjamicroservice.ninja.repositories;

import bg.softuni.ninjamicroservice.ninja.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {

    void deleteAllByDisapprovedTrue();

}
