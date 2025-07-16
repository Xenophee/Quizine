package com.dassonville.api.mapper;


import com.dassonville.api.dto.request.ClassicQuestionInsertDTO;
import com.dassonville.api.dto.request.ClassicQuestionUpdateDTO;
import com.dassonville.api.dto.response.QuestionAdminDTO;
import com.dassonville.api.dto.request.TrueFalseQuestionUpsertDTO;
import com.dassonville.api.model.Question;
import com.dassonville.api.model.QuestionType;
import com.dassonville.api.model.Quiz;
import com.dassonville.api.util.TextUtils;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "spring", uses = {AnswerMapper.class})
public interface QuestionMapper {

    @Mappings({
            @Mapping(target = "text", expression = "java(normalizeText(dto.text()))"),
            @Mapping(target = "answerExplanation", expression = "java(normalizeText(dto.answerExplanation()))"),
            @Mapping(target = "questionType", expression = "java(questionTypeId(dto.type().getType()))"),
            @Mapping(target = "quizzes", expression = "java(mapQuizIdsToQuizzes(List.of(quizId)))"),
            @Mapping(target = "classicAnswers", source = "answers"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    Question toModel(ClassicQuestionInsertDTO dto, @Context long quizId);

    @Mappings({
            @Mapping(target = "text", expression = "java(normalizeText(dto.text()))"),
            @Mapping(target = "answerExplanation", expression = "java(normalizeText(dto.answerExplanation()))"),
            @Mapping(target = "questionType", expression = "java(questionTypeId(dto.type().getType()))"),
            @Mapping(target = "quizzes", expression = "java(mapQuizIdsToQuizzes(List.of(quizId)))"),
            @Mapping(target = "classicAnswers", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    Question toModel(TrueFalseQuestionUpsertDTO dto, @Context long quizId);

    @Mappings({
            @Mapping(target = "answers", source = "classicAnswers")
    })
    QuestionAdminDTO toAdminDTO(Question model);


    @Mappings({
            @Mapping(target = "text", expression = "java(normalizeText(dto.text()))"),
            @Mapping(target = "answerExplanation", expression = "java(normalizeText(dto.answerExplanation()))"),
            @Mapping(target = "quizzes", ignore = true),
            @Mapping(target = "classicAnswers", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true),
            @Mapping(target = "id", ignore = true),
    })
    void updateModelFromDTO(ClassicQuestionUpdateDTO dto, @MappingTarget Question model);

    @Mappings({
            @Mapping(target = "text", expression = "java(normalizeText(dto.text()))"),
            @Mapping(target = "answerExplanation", expression = "java(normalizeText(dto.answerExplanation()))"),
            @Mapping(target = "quizzes", ignore = true),
            @Mapping(target = "classicAnswers", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true),
            @Mapping(target = "id", ignore = true),
    })
    void updateModelFromDTO(TrueFalseQuestionUpsertDTO dto, @MappingTarget Question model);

    @AfterMapping
    default void setQuestionInAnswers(@MappingTarget Question question) {
        if (question.getClassicAnswers() != null) {
            question.getClassicAnswers().forEach(answer -> answer.setQuestion(question));
        }
    }

    default List<Quiz> mapQuizIdsToQuizzes(List<Long> quizIds) {

        if (quizIds == null) return new ArrayList<>();

        List<Quiz> quizzes = new ArrayList<>();
        for (Long quizId : quizIds) {
            quizzes.add(new Quiz(quizId));
        }
        return quizzes;
    }

    @Named("normalizeText")
    default String normalizeText(String input) {
        return TextUtils.normalizeText(input);
    }

    @Named("questionTypeId")
    default QuestionType questionTypeId(String id) {
        return new QuestionType(id);
    }
}
