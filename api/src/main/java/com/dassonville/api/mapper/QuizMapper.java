package com.dassonville.api.mapper;

import com.dassonville.api.dto.*;
import com.dassonville.api.model.Question;
import com.dassonville.api.model.Quiz;
import com.dassonville.api.projection.PublicQuizProjection;
import com.dassonville.api.repository.QuestionRepository;
import com.dassonville.api.util.DateUtils;
import com.dassonville.api.util.TextUtils;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.dassonville.api.constant.AppConstants.MINIMUM_QUIZ_QUESTIONS;
import static com.dassonville.api.constant.AppConstants.NEWNESS_THRESHOLD_DAYS;


@Mapper(componentModel = "spring", uses = {CategoryMapper.class, ThemeMapper.class})
public interface QuizMapper {

    @Mappings({
            @Mapping(target = "title", expression = "java(normalizeText(dto.title()))"),
            @Mapping(target = "category.id", source = "categoryId"),
            @Mapping(target = "theme.id", source = "themeId"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true),
            @Mapping(target = "questions", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    Quiz toModel(QuizUpsertDTO dto);

    @Mappings({
            @Mapping(target = "theme", source = "themeName"),
            @Mapping(target = "category", source = "categoryName"),
            @Mapping(target = "isNew", expression = "java(isQuizNew(projection.getCreatedAt()))")
    })
    QuizPublicDTO toPublicDTO(PublicQuizProjection projection);

    @Mappings({
            @Mapping(target = "theme", source = "theme.name"),
            @Mapping(target = "category", source = "category.name"),
            @Mapping(target = "isNew", expression = "java(isQuizNew(model.getCreatedAt()))"),
            @Mapping(target = "numberOfQuestions", expression = "java(questionRepository.countByQuizIdAndDisabledAtIsNull(model.getId()))"),
    })
    QuizPublicDetailsDTO toPublicDetailsDTO(Quiz model, @Context QuestionRepository questionRepository);

    @Mappings({
            @Mapping(target = "category", source = "category.name"),
            @Mapping(target = "numberOfQuestions", source = "model.questions", qualifiedByName = "mapNumberOfQuestions"),
            @Mapping(target = "hasEnoughQuestionsForActivation", source = "model.questions", qualifiedByName = "mapHasEnoughQuestionsForActivation"),
    })
    QuizAdminDTO toAdminDTO(Quiz model);

    @Mappings({
            @Mapping(target = "categoryId", source = "category.id"),
            @Mapping(target = "themeId", source = "theme.id")
    })
    QuizAdminDetailsDTO toAdminDetailsDTO(Quiz model);


    @Mappings({
            @Mapping(target = "title", expression = "java(normalizeText(dto.title()))"),
            @Mapping(target = "category.id", source = "categoryId"),
            @Mapping(target = "theme.id", source = "themeId"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true),
            @Mapping(target = "questions", ignore = true),
            @Mapping(target = "id", ignore = true),
    })
    void updateModelFromDTO(QuizUpsertDTO dto, @MappingTarget Quiz model);


    default boolean isQuizNew(LocalDateTime createdAt) {
        return DateUtils.isNew(createdAt, NEWNESS_THRESHOLD_DAYS);
    }

    @Named("mapNumberOfQuestions")
    default byte mapNumberOfQuestions(List<Question> questions) {
        return (byte) (questions != null ? questions.size() : 0);
    }

    @Named("mapHasEnoughQuestionsForActivation")
    default boolean mapHasEnoughQuestionsForActivation(List<Question> questions) {
        return questions != null && questions.size() >= MINIMUM_QUIZ_QUESTIONS;
    }

    @Named("normalizeText")
    default String normalizeText(String input) {
        return TextUtils.normalizeText(input);
    }
}
