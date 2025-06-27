package com.dassonville.api.repository;


import com.dassonville.api.model.QuizSessionQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizSessionQuestionRepository extends JpaRepository<QuizSessionQuestion, Long> {

}
