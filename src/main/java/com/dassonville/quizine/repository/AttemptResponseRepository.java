package com.dassonville.quizine.repository;


import com.dassonville.quizine.model.AttemptResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttemptResponseRepository extends JpaRepository<AttemptResponse, Long> {

}
