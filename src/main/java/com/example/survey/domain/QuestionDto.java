package com.example.survey.domain;

import com.example.survey.db.Question;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class QuestionDto {
    private Long id;
    @NotBlank(message = "text cannot be null or blank")
    private String text;
    @NotNull(message = "position cannot be null")
    private Integer position;
    
    public static QuestionDto fromEntity(Question question) {
        return QuestionDto.builder()
                       .id(question.getId())
                       .text(question.getText())
                       .position(question.getPosition())
                       .build();
    }
}
