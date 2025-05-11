package com.dassonville.api.mapper;

import com.dassonville.api.dto.*;
import com.dassonville.api.model.Category;
import com.dassonville.api.model.Question;
import com.dassonville.api.model.Quiz;
import com.dassonville.api.model.Theme;
import com.dassonville.api.util.DateUtils;
import org.mapstruct.*;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

import static com.dassonville.api.constant.AppConstants.NEWNESS_THRESHOLD_DAYS;


@Mapper(componentModel = "spring", uses = {CategoryMapper.class, ThemeMapper.class})
public interface QuizMapper {

    @Mappings({
            @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapCategoryIdToCategory"),
            @Mapping(target = "theme", source = "themeId", qualifiedByName = "mapThemeIdToTheme"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true),
            @Mapping(target = "questions", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    Quiz toModel(QuizUpsertDTO dto);

    QuizPublicDTO toPublicDTO(Quiz quiz);

    List<QuizPublicDTO> toPublicDTOList(List<Quiz> quizzes);

    QuizPublicDetailsDTO toPublicDetailsDTO(QuizPublicDTO quizPublicDTO);

    @Mappings({
            @Mapping(target = "quizzes", expression = "java(toActiveAdminDTOList(theme.getQuizzes()))")
    })
    QuizzesByThemeAdminDTO toQuizzesByTheme(Theme theme);

    @Mappings({
            @Mapping(target = "category", qualifiedByName = "mapIdNameDTOToCategory"),
            @Mapping(target = "numberOfQuestions", source = "quiz.questions", qualifiedByName = "mapNumberOfQuestions")
    })
    QuizActiveAdminDTO toActiveAdminDTO(Quiz quiz);

    @Mappings({
            @Mapping(target = "category", qualifiedByName = "mapIdNameDTOToCategory"),
            @Mapping(target = "theme", qualifiedByName = "mapIdNameDTOToTheme"),
            @Mapping(target = "numberOfQuestions", source = "quiz.questions", qualifiedByName = "mapNumberOfQuestions")
    })
    QuizInactiveAdminDTO toInactiveAdminDTO(Quiz quiz);

    @Mappings({
            @Mapping(target = "categoryId", source = "category.id"),
            @Mapping(target = "themeId", source = "theme.id")
    })
    QuizAdminDetailsDTO toAdminDetailsDTO(Quiz quiz);

    @Mappings({
            @Mapping(target = "categoryId", source = "category.id"),
            @Mapping(target = "themeId", source = "theme.id")
    })
    QuizUpsertDTO toUpsertDTO(Quiz quiz);


    List<QuizActiveAdminDTO> toActiveAdminDTOList(List<Quiz> quizzes);
    List<QuizInactiveAdminDTO> toInactiveAdminDTOList(List<Quiz> quizzes);


    @Mappings({
            @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapCategoryIdToCategory"),
            @Mapping(target = "theme", source = "themeId", qualifiedByName = "mapThemeIdToTheme"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true),
            @Mapping(target = "questions", ignore = true),
            @Mapping(target = "id", ignore = true),
    })
    void updateModelFromDTO(QuizUpsertDTO dto, @MappingTarget Quiz quiz);

    default boolean isQuizNew(LocalDate createdAt) {
        return DateUtils.isNew(createdAt, NEWNESS_THRESHOLD_DAYS);
    }


    @AfterMapping
    default void capitalizeTitle(@MappingTarget Quiz quiz) {
        if (quiz.getTitle() != null) {
            quiz.setTitle(StringUtils.capitalize(quiz.getTitle().trim()));
        }
    }


    @Named("mapCategoryIdToCategory")
    default Category mapCategoryIdToCategory(long categoryId) {
        return new Category(categoryId);
    }

    @Named("mapThemeIdToTheme")
    default Theme mapThemeIdToTheme(long themeId) {
        return new Theme(themeId);
    }

    @Named("mapIdNameDTOToCategory")
    default IdNameDTO mapIdNameDTOToCategory(Category category) {
        return new IdNameDTO(category.getId(), category.getName());
    }

    @Named("mapIdNameDTOToTheme")
    default IdNameDTO mapIdNameDTOToTheme(Theme theme) {
        return new IdNameDTO(theme.getId(), theme.getName());
    }

    @Named("mapNumberOfQuestions")
    default byte mapNumberOfQuestions(List<Question> questions) {
        return (byte) (questions != null ? questions.size() : 0);
    }
}
