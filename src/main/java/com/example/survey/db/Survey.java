package com.example.survey.db;

import com.example.survey.domain.SurveyDto;
import com.example.survey.domain.validators.SurveyValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "surveys")
public class Survey {
    @Id
    @SequenceGenerator(name = "generator", sequenceName = "general_sq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "active_from", nullable = false)
    private LocalDateTime activeFrom;
    
    @Column(name = "active_to", nullable = false)
    private LocalDateTime activeTo;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    
    @OrderBy("position")
    @OneToMany(mappedBy = "survey", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;
    
    public static Survey create(SurveyDto dto) {
        Survey survey = Survey.builder()
                                .name(dto.getName())
                                .activeFrom(dto.getActiveFrom())
                                .activeTo(dto.getActiveTo())
                                .isActive(dto.getIsActive())
                                .build();
        
        dto.getQuestions().forEach(questionDto -> {
            if (CollectionUtils.isEmpty(survey.getQuestions())) {
                survey.setQuestions(new ArrayList<>());
            }
            Question question = Question.fromDto(questionDto);
            question.setSurvey(survey);
            survey.getQuestions().add(question);
        });
        
        SurveyValidator.check(survey);
        return survey;
    }
    
    public void edit(SurveyDto dto) {
        this.setName(dto.getName());
        this.setActiveFrom(dto.getActiveFrom());
        this.setActiveTo(dto.getActiveTo());
        this.setIsActive(dto.getIsActive());
        this.getQuestions().clear();
        
        dto.getQuestions().forEach(questionDto -> {
            Question question = Question.fromDto(questionDto);
            question.setSurvey(this);
            this.getQuestions().add(question);
        });
    
        SurveyValidator.check(this);
    }
    
}
