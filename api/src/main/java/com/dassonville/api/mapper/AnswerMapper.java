package com.dassonville.api.mapper;

import com.dassonville.api.dto.AnswerAdminDTO;
import com.dassonville.api.dto.AnswerUpsertDTO;
import com.dassonville.api.model.Answer;
import com.dassonville.api.model.Question;
import org.mapstruct.*;
import org.springframework.util.StringUtils;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    @Mappings({
            @Mapping(target = "question", expression = "java(mapQuestionIdToQuestion(questionId))"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    Answer toModel(AnswerUpsertDTO dto, @Context long questionId);


    AnswerUpsertDTO toUpsertDTO(Answer answer);

    AnswerAdminDTO toAdminDTO(Answer answer);

    @Mappings({
            @Mapping(target = "question", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    void updateModelFromDTO(AnswerUpsertDTO dto, @MappingTarget Answer answer);


    @AfterMapping
    default void capitalizeText(@MappingTarget Answer answer) {
        if (answer.getText() != null) {
            answer.setText(StringUtils.capitalize(answer.getText().trim()));
        }
    }


    default Question mapQuestionIdToQuestion(long questionId) {
        return new Question(questionId);
    }
}
