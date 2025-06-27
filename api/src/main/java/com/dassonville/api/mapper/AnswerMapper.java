package com.dassonville.api.mapper;

import com.dassonville.api.dto.response.AnswerAdminDTO;
import com.dassonville.api.dto.request.ClassicAnswerUpsertDTO;
import com.dassonville.api.model.ClassicAnswer;
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
    ClassicAnswer toModel(ClassicAnswerUpsertDTO dto, @Context long questionId);

    AnswerAdminDTO toAdminDTO(ClassicAnswer classicAnswer);

    @Mappings({
            @Mapping(target = "text", expression = "java(normalizeText(dto.text()))"),
            @Mapping(target = "question", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    void updateModelFromDTO(ClassicAnswerUpsertDTO dto, @MappingTarget ClassicAnswer classicAnswer);


    default Question mapQuestionIdToQuestion(long questionId) {
        return new Question(questionId);
    }

    @Named("normalizeText")
    default String normalizeText(String input) {
        return TextUtils.normalizeText(input);
    }
}
