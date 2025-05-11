package com.dassonville.api.mapper;


import com.dassonville.api.dto.QuestionAdminDTO;
import com.dassonville.api.dto.QuestionInsertDTO;
import com.dassonville.api.dto.QuestionUpdateDTO;
import com.dassonville.api.model.Question;
import com.dassonville.api.model.Quiz;
import org.mapstruct.*;
import org.springframework.util.StringUtils;

@Mapper(componentModel = "spring", uses = {AnswerMapper.class})
public interface QuestionMapper {

    @Mappings({
            @Mapping(target = "quiz", expression = "java(mapQuizIdToQuiz(quizId))"),
            @Mapping(target = "answers", source = "answers"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    Question toModel(QuestionInsertDTO questionInsertDTO, @Context long quizId);

    QuestionInsertDTO toInsertDTO(Question question);

    QuestionAdminDTO toAdminDTO(Question question);

    @Mappings({
            @Mapping(target = "quiz", ignore = true),
            @Mapping(target = "answers", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    void updateModelFromDTO(QuestionUpdateDTO questionUpdateDTO, @MappingTarget Question question);


    @AfterMapping
    default void capitalizeText(@MappingTarget Question question) {
        if (question.getText() != null) {
            question.setText(StringUtils.capitalize(question.getText().trim()));
        }
    }

    @AfterMapping
    default void setQuestionInAnswers(@MappingTarget Question question) {
        if (question.getAnswers() != null) {
            question.getAnswers().forEach(answer -> answer.setQuestion(question));
        }
    }


    default Quiz mapQuizIdToQuiz(long quizId) {
        return new Quiz(quizId);
    }
}
