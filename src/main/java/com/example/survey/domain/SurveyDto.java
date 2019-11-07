package com.example.survey.domain;

import com.example.survey.db.Survey;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class SurveyDto {
    private Long id;
    @NotBlank(message = "name cannot be null or blank")
    private String name;
    @NotNull(message = "active from cannot be null")
    private LocalDateTime activeFrom;
    @NotNull(message = "active to cannot be null")
    private LocalDateTime activeTo;
    @NotNull(message = "is active cannot be null")
    private Boolean isActive;
    @NotEmpty(message = "questions cannot be null or empty")
    private List<QuestionDto> questions;
    
    public static SurveyDto fromEntity(Survey survey) {
        List<QuestionDto> questionDtos = survey.getQuestions().stream().collect(
                ArrayList::new,
                (result, question) -> result.add(QuestionDto.fromEntity(question)),
                ArrayList::addAll);
        return SurveyDto.builder()
                       .id(survey.getId())
                       .name(survey.getName())
                       .activeFrom(survey.getActiveFrom())
                       .activeTo(survey.getActiveTo())
                       .isActive(survey.getIsActive())
                       .questions(questionDtos)
                       .build();
    }
}
