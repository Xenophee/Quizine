package com.dassonville.api.repository;

import com.dassonville.api.model.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {

    boolean existsByTextIgnoreCase(String text);

    boolean existsByTextIgnoreCaseAndIdNot(String text, long id);

    boolean existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(long questionId, long id);

    int countByQuestionIdAndDisabledAtIsNull(long questionId);
}
