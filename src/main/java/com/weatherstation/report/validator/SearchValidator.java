package com.weatherstation.report.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.weatherstation.report.dto.SearchRequestDto;

public class SearchValidator implements Validator {
    @Override
    public boolean supports(final Class clazz) {
        return SearchRequestDto.class.equals(clazz);
    }

	@Override
	public void validate(Object target, Errors errors) {
		final SearchRequestDto searchRequest = (SearchRequestDto) target;

        if (searchRequest.getStartDate() == null) {
            errors.rejectValue("startDate", "Start date is required");
        }
        if (searchRequest.getEndDate() == null) {
            errors.rejectValue("endDate", "End date is required");
        }
        if (searchRequest.getStartDate() != null && searchRequest.getEndDate() != null
                && searchRequest.getStartDate().getTime() > searchRequest.getEndDate().getTime()) {
            errors.rejectValue("endDate", "End date must be greater than or equals to Start date");
        }
		
	}
}