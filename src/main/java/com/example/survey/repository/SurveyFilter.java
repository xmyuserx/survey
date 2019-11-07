package com.example.survey.repository;

import com.querydsl.core.types.dsl.BooleanExpression;

import java.time.LocalDateTime;

import static com.example.survey.db.QSurvey.survey;
import static java.util.Objects.nonNull;

public class SurveyFilter {
    private BooleanExpression expression;
    
    public static SurveyFilter newFilter() {
        return new SurveyFilter();
    }
    
    private void and(BooleanExpression expression) {
        if (this.expression == null) {
            this.expression = expression;
        } else {
            this.expression = this.expression.and(expression);
        }
    }
    
    public SurveyFilter name(String name) {
        if (nonNull(name)) {
            and(survey.name.like('%' + name + '%'));
        }
        return this;
    }
    
    public SurveyFilter activeOn(LocalDateTime activeOn) {
        if (nonNull(activeOn)) {
            and(survey.activeFrom.loe(activeOn).and(survey.activeTo.goe(activeOn)));
        }
        return this;
    }
    
    public SurveyFilter isActive(Boolean isActive) {
        if (nonNull(isActive)) {
            and(survey.isActive.eq(isActive));
        }
        return this;
    }
    
    public BooleanExpression build() {
        return expression;
    }
}
