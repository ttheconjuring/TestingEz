package com.testingez.testingez.repositories;

import com.testingez.testingez.models.entities.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {

    List<Response> findAllByTestIdAndUserId(Long test_id, Long user_id);

}
