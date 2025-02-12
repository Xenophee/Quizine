package com.dassonville.quizine.repository;

import com.dassonville.quizine.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    boolean existsByTitle(String title);

    Optional<Quiz> findByTitle(String title);
}
