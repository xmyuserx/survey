package com.example.survey.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SurveyFilterDto {
    private Integer page;
    private Integer rows;
    private String sort;
    private String name;
    private LocalDateTime activeOn;
    private Boolean isActive;
}
