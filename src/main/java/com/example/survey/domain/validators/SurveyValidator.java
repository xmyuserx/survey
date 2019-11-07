package com.example.survey.domain.validators;

import com.example.survey.db.Survey;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

public class SurveyValidator {
    
    public static void check(Survey survey) {
        Assert.isTrue(survey.getActiveFrom().isBefore(survey.getActiveTo()),
                "active from should be earlier than active to");
        Assert.isTrue(survey.getActiveTo().isAfter(LocalDateTime.now()),
                "active to should be later than now");
    }
    
}
