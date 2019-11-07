package com.example.survey.service;

import com.example.survey.db.Survey;
import com.example.survey.domain.SurveyDto;
import com.example.survey.domain.SurveyFilterDto;
import com.example.survey.repository.SurveyFilter;
import com.example.survey.repository.SurveyRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    
    @Transactional
    public SurveyDto create(SurveyDto dto) {
        Survey survey = Survey.create(dto);
        surveyRepository.save(survey);
        return SurveyDto.fromEntity(survey);
    }
    
    @Transactional(readOnly = true)
    public Page<SurveyDto> getAll(SurveyFilterDto dto) {
        PageRequest pageRequest = PageRequest.of(dto.getPage() - 1, dto.getRows(), Sort.by(dto.getSort()));
        BooleanExpression filter = SurveyFilter.newFilter()
                                           .name(dto.getName())
                                           .activeOn(dto.getActiveOn())
                                           .isActive(dto.getIsActive())
                                           .build();
        Page<Survey> page;
        if (filter == null) {
            page = surveyRepository.findAll(pageRequest);
        } else {
            page = surveyRepository.findAll(filter, pageRequest);
        }
        return page.map(SurveyDto::fromEntity);
    }
    
    @Transactional
    public SurveyDto edit(SurveyDto dto) {
        Survey survey = surveyRepository.findById(dto.getId()).orElseThrow(
                () -> new IllegalArgumentException("survey is not found"));
        survey.edit(dto);
        surveyRepository.save(survey);
        return SurveyDto.fromEntity(survey);
    }
    
    @Transactional
    public void delete(Long id) {
        Survey survey = surveyRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("survey is not found"));
        surveyRepository.delete(survey);
    }
}
