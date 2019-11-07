package com.example.survey.api;

import com.example.survey.domain.SurveyDto;
import com.example.survey.domain.SurveyFilterDto;
import com.example.survey.service.SurveyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@RestController
@AllArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;
    
    @PostMapping("/survey")
    @ResponseStatus(HttpStatus.CREATED)
    public SurveyDto create(@RequestBody @Valid SurveyDto dto) {
        return surveyService.create(dto);
    }
    
    @GetMapping("/survey")
    @ResponseStatus(HttpStatus.OK)
    public Page<SurveyDto> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "rows", required = false, defaultValue = "20") Integer rows,
            @RequestParam(value = "sort", required = false, defaultValue = "id") String sort,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "activeOn", required = false) @DateTimeFormat(iso = DATE_TIME) LocalDateTime activeOn,
            @RequestParam(value = "isActive", required = false) Boolean isActive) {
        SurveyFilterDto dto = SurveyFilterDto.builder()
                                      .page(page)
                                      .rows(rows)
                                      .sort(sort)
                                      .name(name)
                                      .activeOn(activeOn)
                                      .isActive(isActive)
                                      .build();
        return surveyService.getAll(dto);
    }
    
    @PutMapping("/survey/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SurveyDto edit(
            @PathVariable("id") Long id,
            @RequestBody @Valid SurveyDto dto) {
        dto.setId(id);
        return surveyService.edit(dto);
    }
    
    @DeleteMapping("/survey/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        surveyService.delete(id);
    }
    
}
