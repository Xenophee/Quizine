package com.dassonville.api.repository;

import com.dassonville.api.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    boolean existsByTextIgnoreCaseAndQuestionId(String text, long questionId);

    boolean existsByTextIgnoreCaseAndIdNot(String text, long id);

    boolean existsByQuestionId(long questionId);

    boolean existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(long questionId, long id);

    int countByQuestionIdAndDisabledAtIsNull(long questionId);
}
