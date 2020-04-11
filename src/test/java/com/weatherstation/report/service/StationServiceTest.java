package com.weatherstation.report.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.format.datetime.DateFormatter;
import com.weatherstation.report.dto.StationDto;
import com.weatherstation.report.entity.Station;
import com.weatherstation.report.repositories.StationRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.Date;
import java.util.Locale;

public class StationServiceTest {

    @InjectMocks
    StationService stationService;
    @Mock
    StationRepository stationRepository = mock(StationRepository.class);
    DateFormatter dateFormatter = new DateFormatter("yyyy-MM-dd");

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void Test_GetAllStations_When_Return_AllRecords() throws ParseException {
        final List<Station> stations = new ArrayList<Station>();
        stations.add(new Station("Station1", "ON", new Date(dateFormatter.parse("2020-04-10", Locale.CANADA).getTime()),
                30.00, 40.21, 20.19));
        when(stationRepository.findAll()).thenReturn(stations);
        final List<StationDto> result = stationService.getAllStations();
        assertEquals(stations, result, "Expected single station with dated 2020-04-10");
    }

    @Test
    public void Test_GetAllStations_When_Return_NoRecords() {
        final List<Station> mockData = new ArrayList<Station>();
        when(stationRepository.findAll()).thenReturn(mockData);
        final List<StationDto> result = stationService.getAllStations();
        assertEquals(mockData, result, "Expected no stations");
    }

    @Test
    public void Test_SearchStations_When_Return_AllRecords() throws ParseException {
        final List<Station> stations = new ArrayList<Station>();
        stations.add(new Station("Station1", "ON", new Date(dateFormatter.parse("2020-04-10", Locale.CANADA).getTime()),
                30.00, 40.21, 20.19));
        stations.add(new Station("Station2", "ON", new Date(dateFormatter.parse("2020-04-11", Locale.CANADA).getTime()),
                30.00, 40.21, 20.19));
        when(stationRepository.search(any(Date.class), any(Date.class))).thenReturn(stations);
        final List<StationDto> result = stationService.searchStations(100, 200);
        assertEquals(stations.size(), result.size(), "Expected single station with dated 2020-04-10");
        assertEquals(stations.get(0).getName(), "Station1", "Expected 1st result as Station1");
        assertEquals(stations.get(1).getName(), "Station2", "Expected 1st result as Station2");
    }

    @Test
    public void Test_SearchAllStations_When_Return_NoRecords() {
        final List<Station> mockData = new ArrayList<Station>();
        when(stationRepository.search(any(Date.class), any(Date.class))).thenReturn(mockData);
        final List<StationDto> result = stationService.searchStations(100, 200);
        assertEquals(mockData, result, "Expected no stations");
    }

    @Test
    public void Test_SearchAllStations_When_StartDate_LessThan_EndDate_ThrowsException() {
        
         Throwable exception = assertThrows(IllegalArgumentException.class, () ->
          stationService.searchStations(200, 100));
          assertEquals("End date must be greater or equals to start date",
          exception.getMessage());
         /*
        try {
            stationService.searchStations(200, 100);
        } catch (final SearchDateException exp) {
            assertNotNull(exp);
        }*/
    }
}
