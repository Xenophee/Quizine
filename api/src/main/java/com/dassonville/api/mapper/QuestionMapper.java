package com.dassonville.api.mapper;


import com.dassonville.api.dto.QuestionAdminDTO;
import com.dassonville.api.dto.QuestionInsertDTO;
import com.dassonville.api.dto.QuestionUpdateDTO;
import com.dassonville.api.model.Question;
import com.dassonville.api.model.Quiz;
import com.dassonville.api.util.TextUtils;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {AnswerMapper.class})
public interface QuestionMapper {

    @Mappings({
            @Mapping(target = "text", expression = "java(normalizeText(dto.text()))"),
            @Mapping(target = "quiz", expression = "java(mapQuizIdToQuiz(quizId))"),
            @Mapping(target = "answers", source = "answers"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    Question toModel(QuestionInsertDTO dto, @Context long quizId);

    @Mappings({
            @Mapping(target = "text", source = "text"),
    })
    QuestionAdminDTO toAdminDTO(Question model);


    @Mappings({
            @Mapping(target = "text", expression = "java(normalizeText(dto.text()))"),
            @Mapping(target = "quiz", ignore = true),
            @Mapping(target = "answers", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true),
            @Mapping(target = "id", ignore = true),
    })
    void updateModelFromDTO(QuestionUpdateDTO dto, @MappingTarget Question model);



    @AfterMapping
    default void setQuestionInAnswers(@MappingTarget Question question) {
        if (question.getAnswers() != null) {
            question.getAnswers().forEach(answer -> answer.setQuestion(question));
        }
    }


    default Quiz mapQuizIdToQuiz(long quizId) {
        return new Quiz(quizId);
    }

    @Named("normalizeText")
    default String normalizeText(String input) {
        return TextUtils.normalizeText(input);
    }
}
