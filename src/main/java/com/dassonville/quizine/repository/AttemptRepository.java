package com.dassonville.quizine.repository;

import com.dassonville.quizine.model.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Long> {
}
