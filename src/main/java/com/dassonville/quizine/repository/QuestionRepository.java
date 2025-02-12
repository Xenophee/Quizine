package com.dassonville.quizine.repository;


import com.dassonville.quizine.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    boolean existsByQuestion(String question);

    Optional<Question> findByQuestion(String question);
}
