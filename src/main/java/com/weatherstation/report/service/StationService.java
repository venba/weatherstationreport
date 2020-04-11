package com.weatherstation.report.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weatherstation.report.dto.StationDto;
import com.weatherstation.report.entity.Station;
import com.weatherstation.report.exception.SearchDateException;
import com.weatherstation.report.mapper.StationMapper;
import com.weatherstation.report.repositories.StationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Component
public class StationService {
    @Autowired
    StationRepository stationRepository;

    private static final Logger logger = LogManager.getLogger(StationService.class);

    public List<StationDto> getAllStations() {
        // get all records from database
        final List<Station> stations = stationRepository.findAll();
        // map the entity to dto
        return StationMapper.convertToDto(stations);
    }

    public List<StationDto> searchStations(final long startDate, final long endDate) throws SearchDateException {

        // validates the start date must be always lesser or equals to end date
        if (startDate > endDate) {
            logger.debug("given dates are not valid, rejecting the request {} and {}", startDate, endDate);
            throw new SearchDateException("End date must be greater or equals to start date");
            
        }
        // get the search result from database
        final List<Station> result = stationRepository.search(new Date(startDate), new Date(endDate));
        // map the entity to dto
        return StationMapper.convertToDto(result);
    }

    public StationDto getStationById(final long stationId) {
        final Optional<Station> station = stationRepository.findById(stationId);
        if (station.isPresent()) {
            return StationMapper.convertToDto(station.get());
        }
        return null;
    }

}