package com.dassonville.api.service;


import com.dassonville.api.projection.CodeAndNameProjection;
import com.dassonville.api.repository.QuizTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizTypeService {

    private final QuizTypeRepository quizTypeRepository;


    /**
     * Récupère tous les types de quiz disponibles dans la base de données.
     *
     * @return une liste de {@link CodeAndNameProjection } représentant les types de quiz par ID et nom, le tout trié par nom.
     */
    public List<CodeAndNameProjection> findAllQuizTypes() {
        return quizTypeRepository.findAllByOrderByName();
    }
}
