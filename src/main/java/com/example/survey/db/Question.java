package com.example.survey.db;

import com.example.survey.domain.QuestionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "questions")
public class Question {
    @Id
    @SequenceGenerator(name = "generator", sequenceName = "general_sq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id", referencedColumnName = "id", nullable = false)
    private Survey survey;
    
    @Column(name = "text", nullable = false)
    private String text;
    
    @Column(name = "position", nullable = false)
    private Integer position;
    
    public static Question fromDto(QuestionDto dto) {
        return Question.builder()
                       .text(dto.getText())
                       .position(dto.getPosition())
                       .build();
    }
}
