package com.weatherstation.report.mapper;

import java.util.List;


import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import com.weatherstation.report.dto.StationDto;
import com.weatherstation.report.entity.Station;

public class StationMapper {
    public static StationDto convertToDto(final Station station) {
        final ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(station, StationDto.class);
    }

    public static List<StationDto> convertToDto(final List<Station> stations) {
        final ModelMapper modelMapper = new ModelMapper();
        final java.lang.reflect.Type targetListType = new TypeToken<List<Station>>() {
        }.getType();
        return modelMapper.map(stations, targetListType);
    }
}