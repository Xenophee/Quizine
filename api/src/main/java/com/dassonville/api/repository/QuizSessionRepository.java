package com.dassonville.api.repository;

import com.dassonville.api.model.QuizSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuizSessionRepository extends JpaRepository<QuizSession, Long> {
}
