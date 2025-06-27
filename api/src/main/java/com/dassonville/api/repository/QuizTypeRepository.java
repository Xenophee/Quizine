package com.dassonville.api.repository;


import com.dassonville.api.model.QuizType;
import com.dassonville.api.projection.CodeAndNameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizTypeRepository extends JpaRepository<QuizType, String>  {

    /**
     * Récupère l'ID du type de quiz pour un quiz actif donné.
     * Le thème, la catégorie, le niveau de maîtrise et le type de quiz doivent également être actifs.
     *
     * @param quizId L'ID du quiz.
     * @return Un {@code Optional} contenant l'ID du type de quiz s'il est actif, sinon vide.
     */
    @Query("""
            SELECT type.code FROM QuizType type
                JOIN type.quizzes quiz
                JOIN quiz.theme theme
                LEFT JOIN quiz.category category
                JOIN quiz.masteryLevel masteryLevel
            WHERE quiz.id = :quizId
                AND quiz.disabledAt IS NULL
                AND type.disabledAt IS NULL
                AND theme.disabledAt IS NULL
                AND category.disabledAt IS NULL
                AND masteryLevel.disabledAt IS NULL
            """)
    Optional<String> findQuizTypeCodeByQuizIdAndDisabledAtIsNull(long quizId);


    /**
     * Récupère tous les types de quiz, triés par nom.
     *
     * @return Une liste de {@link CodeAndNameProjection } contenant l'ID et le nom des types de quiz.
     */
    List<CodeAndNameProjection> findAllByOrderByName();
}
