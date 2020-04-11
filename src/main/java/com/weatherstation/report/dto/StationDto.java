package com.weatherstation.report.dto;

import java.util.Date;

public class StationDto {
    private long id;
    private String name;
    private String province;
    private Date date;
    private Double mean_temp;
    private Double highest_Monthly_Maxi_Temp;
    private Double lowest_Monthly_Min_Temp;

    public StationDto() {

    }

    public StationDto(final String name, final String province, final Date date, final Double mean_temp,
            final Double highest_Monthly_Maxi_Temp, final Double lowest_Monthly_Min_Temp) {
        super();
        this.name = name;
        this.province = province;
        this.date = date;
        this.mean_temp = mean_temp;
        this.highest_Monthly_Maxi_Temp = highest_Monthly_Maxi_Temp;
        this.lowest_Monthly_Min_Temp = lowest_Monthly_Min_Temp;
    }

    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(final String province) {
        this.province = province;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public Double getMean_temp() {
        return this.mean_temp;
    }

    public void setMean_temp(final Double mean_temp) {
        this.mean_temp = mean_temp;
    }

    public Double getHighest_Monthly_Maxi_Temp() {
        return this.highest_Monthly_Maxi_Temp;
    }

    public void setHighest_Monthly_Maxi_Temp(final Double highest_Monthly_Maxi_Temp) {
        this.highest_Monthly_Maxi_Temp = highest_Monthly_Maxi_Temp;
    }

    public Double getLowest_Monthly_Min_Temp() {
        return this.lowest_Monthly_Min_Temp;
    }

    public void setLowest_Monthly_Min_Temp(final Double lowest_Monthly_Min_Temp) {
        this.lowest_Monthly_Min_Temp = lowest_Monthly_Min_Temp;
    }

}