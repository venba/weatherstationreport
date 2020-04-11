package com.weatherstation.report.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class SearchRequestDto {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date startDate;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date endDate;

    public SearchRequestDto() {

    }

    public SearchRequestDto(final Date startDate, final Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }
}