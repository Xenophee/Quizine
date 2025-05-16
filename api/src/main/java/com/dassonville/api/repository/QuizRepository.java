package com.dassonville.api.repository;

import com.dassonville.api.model.Quiz;
import com.dassonville.api.projection.PublicQuizProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    /*String FIND_ALL_ACTIVE_PUBLIC_QUIZZES = """
    SELECT quiz.id, quiz.title, quiz.isVipOnly, quiz.createdAt,
           category.id, category.name,
           theme.id,
           COUNT(question.id)
    FROM Quiz quiz
    JOIN quiz.category category
    JOIN quiz.theme theme
    LEFT JOIN quiz.questions question
    WHERE quiz.disabledAt IS NULL
    GROUP BY quiz.id, quiz.title, quiz.isVipOnly, quiz.createdAt,
             category.id, category.name,
             theme.id
    """;*/


    boolean existsByTitleIgnoreCase(String title);

    boolean existsByTitleIgnoreCaseAndIdNot(String title, long id);

    int countByThemeIdAndDisabledAtIsNull(long themeId);

    /*@Query(FIND_ALL_ACTIVE_PUBLIC_QUIZZES)
    List<PublicQuizProjection> findByDisabledAtIsNull();*/

    List<Quiz> findAllByDisabledAtIsNotNull();

    List<Quiz> findByThemeIdAndDisabledAtIsNull(long themeId);
}
