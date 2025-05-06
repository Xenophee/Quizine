package com.dassonville.api.repository;


import com.dassonville.api.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    boolean existsByQuizIdAndTextIgnoreCase(long quizId, String text);

    boolean existsByQuizIdAndTextIgnoreCaseAndIdNot(long quizId, String text, long id);

    int countByQuizIdAndDisabledAtIsNull(long quizId);
}
