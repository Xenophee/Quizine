package com.dassonville.api.mapper;

import com.dassonville.api.dto.AnswerAdminDTO;
import com.dassonville.api.dto.AnswerUpsertDTO;
import com.dassonville.api.model.Answer;
import com.dassonville.api.model.Question;
import com.dassonville.api.util.TextUtils;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    @Mappings({
            @Mapping(target = "text", expression = "java(normalizeText(dto.text()))"),
            @Mapping(target = "question", expression = "java(mapQuestionIdToQuestion(questionId))"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    Answer toModel(AnswerUpsertDTO dto, @Context long questionId);

    AnswerAdminDTO toAdminDTO(Answer answer);

    @Mappings({
            @Mapping(target = "text", expression = "java(normalizeText(dto.text()))"),
            @Mapping(target = "question", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    void updateModelFromDTO(AnswerUpsertDTO dto, @MappingTarget Answer answer);


    default Question mapQuestionIdToQuestion(long questionId) {
        return new Question(questionId);
    }

    @Named("normalizeText")
    default String normalizeText(String input) {
        return TextUtils.normalizeText(input);
    }
}
