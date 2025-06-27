package com.dassonville.api.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Représente le résultat de la vérification d'une réponse à une question de quiz.",
        oneOf = {
                CheckClassicAnswerResultDTO.class,
                CheckTrueFalseAnswerResultDTO.class
        })
public interface CheckAnswerResultDTO {
    @Schema(name = "isCorrect", description = "Indique si le joueur a répondu correctement.", example = "true")
    boolean isCorrect();

    @Schema(name = "isInTime", description = "Indique si la réponse a été donnée dans le temps imparti.", example = "true")
    boolean isInTime();

    @Schema(description = "Score obtenu pour la réponse.", example = "10")
    int score();

    @Schema(description = "Message décrivant le résultat de la vérification de la réponse.", example = "Bonne réponse !")
    String resultMessage();

    @Schema(description = "Explication de la réponse.", example = "La bonne réponse est 'Paris' car c'est la capitale de la France.")
    String answerExplanation();
}
