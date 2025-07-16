package com.dassonville.api.mapper;

import com.dassonville.api.constant.GameType;
import com.dassonville.api.constant.Type;
import com.dassonville.api.dto.request.QuizUpsertDTO;
import com.dassonville.api.dto.response.QuizAdminDTO;
import com.dassonville.api.dto.response.QuizAdminDetailsDTO;
import com.dassonville.api.dto.response.QuizPublicDTO;
import com.dassonville.api.dto.response.QuizPublicDetailsDTO;
import com.dassonville.api.model.*;
import com.dassonville.api.projection.PublicQuizProjection;
import com.dassonville.api.repository.QuestionRepository;
import com.dassonville.api.util.DateUtils;
import com.dassonville.api.util.TextUtils;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.dassonville.api.constant.AppConstants.MIN_ACTIVE_QUESTIONS_PER_QUIZ;
import static com.dassonville.api.constant.AppConstants.NEWNESS_THRESHOLD_DAYS;


@Mapper(componentModel = "spring", uses = {CategoryMapper.class, ThemeMapper.class, QuestionMapper.class, GameType.class})
public interface QuizMapper {

    @Mappings({
            @Mapping(target = "title", expression = "java(normalizeText(dto.title()))"),
            @Mapping(target = "quizType.code", source = "type", qualifiedByName = "mapGameTypeToCode"),
            @Mapping(target = "masteryLevel.id", source = "masteryLevelId"),
            @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapNewCategory"),
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
            @Mapping(target = "quizType", source = "quizTypeName"),
            @Mapping(target = "masteryLevel", source = "masteryLevelName"),
            @Mapping(target = "isNew", expression = "java(isQuizNew(projection.getCreatedAt()))")
    })
    QuizPublicDTO toPublicDTO(PublicQuizProjection projection);

    @Mappings({
            @Mapping(target = "theme", source = "theme.name"),
            @Mapping(target = "category", source = "category.name"),
            @Mapping(target = "quizType", source = "quizType.name"),
            @Mapping(target = "masteryLevel", source = "masteryLevel.name"),
            @Mapping(target = "isNew", expression = "java(isQuizNew(model.getCreatedAt()))"),
            @Mapping(target = "numberOfQuestions", expression = "java(questionRepository.countByQuizzesIdAndDisabledAtIsNull(model.getId()))"),
    })
    QuizPublicDetailsDTO toPublicDetailsDTO(Quiz model, @Context QuestionRepository questionRepository);

    @Mappings({
            @Mapping(target = "category", source = "category.name"),
            @Mapping(target = "quizType", source = "quizType.name"),
            @Mapping(target = "masteryLevel", source = "masteryLevel.name"),
            @Mapping(target = "numberOfQuestions", source = "model.questions", qualifiedByName = "mapNumberOfQuestions"),
            @Mapping(target = "hasEnoughQuestionsForActivation", source = "model.questions", qualifiedByName = "mapHasEnoughQuestionsForActivation"),
    })
    QuizAdminDTO toAdminDTO(Quiz model);

    @Mappings({
            @Mapping(target = "categoryId", source = "category.id"),
            @Mapping(target = "themeId", source = "theme.id"),
            @Mapping(target = "masteryLevelId", source = "masteryLevel.id"),
            @Mapping(target = "quizType", source = "quizType.name"),
    })
    QuizAdminDetailsDTO toAdminDetailsDTO(Quiz model);


    @Mappings({
            @Mapping(target = "title", expression = "java(normalizeText(dto.title()))"),
            @Mapping(target = "description", expression = "java(normalizeText(dto.description()))"),
            @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapNewCategory"),
            @Mapping(target = "theme", source = "themeId", qualifiedByName = "mapNewThemeForUpdate"),
            @Mapping(target = "masteryLevel", source = "masteryLevelId", qualifiedByName = "mapNewMasteryLevelForUpdate"),
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
        return questions != null && questions.size() >= MIN_ACTIVE_QUESTIONS_PER_QUIZ;
    }

    @Named("normalizeText")
    default String normalizeText(String input) {
        return TextUtils.normalizeText(input);
    }

    @Named("mapGameTypeToCode")
    default String mapGameTypeToCode(Type type) {
        return type.getValue();
    }

    @Named("mapNewThemeForUpdate")
    default Theme mapNewThemeForUpdate(long themeId) {
        return new Theme(themeId);
    }

    @Named("mapNewCategory")
    default Category mapNewCategory(Long categoryId) {
        if (categoryId == null) return null;
        return new Category(categoryId);
    }

    @Named("mapNewMasteryLevelForUpdate")
    default MasteryLevel mapNewMasteryLevelForUpdate(long masteryLevelId) {
        return new MasteryLevel(masteryLevelId);
    }

}
