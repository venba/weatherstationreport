package com.weatherstation.report.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import com.weatherstation.report.dto.StationDto;
import com.weatherstation.report.service.StationService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import java.util.Date;
import java.util.Locale;


import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;

@WebMvcTest(ClimateController.class)
public class ClimateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StationService stationService;
    DateFormatter dateFormatter = new DateFormatter("yyyy-MM-dd");

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_Index_Page_WithStations() throws Exception {
        final List<StationDto> stations = new ArrayList<StationDto>();
        stations.add(new StationDto("Station1", "ON",
                new Date(dateFormatter.parse("2020-04-10", Locale.CANADA).getTime()), 30.00, 40.21, 20.19));
        when(stationService.getAllStations()).thenReturn(stations);
        final RequestBuilder request = MockMvcRequestBuilders.get("/");

        // get the result from controller
        final ModelAndView result = mockMvc.perform(request).andExpect(status().isOk()).andReturn().getModelAndView();

        // assert the view name
        assertEquals(result.getViewName(), "index");
        final List<StationDto> stationsInResult = (List<StationDto>) result.getModel().get("stations");
        assertEquals(stations, stationsInResult, "Stations are retrieved");
        assertEquals(stationsInResult.get(0).getName(), "Station1", "Expected 1st result as Station1");
    }

    @Test
    public void test_Index_Page_WithoutStations() throws Exception {
        final List<StationDto> stations = new ArrayList<StationDto>();
        when(stationService.getAllStations()).thenReturn(stations);
        final RequestBuilder request = MockMvcRequestBuilders.get("/");
        // get the result from controller
        final ModelAndView result = mockMvc.perform(request).andExpect(status().isOk()).andReturn().getModelAndView();

        // assert the view name
        assertEquals(result.getViewName(), "index");
        final List<StationDto> stationsInResult = (List<StationDto>) result.getModel().get("stations");
        assertEquals(0, stationsInResult.size(), "Received no Stations");
    }

    @Test
    public void test_Search_With_NoDates() throws Exception {
        final List<StationDto> stations = new ArrayList<StationDto>();
        when(stationService.getAllStations()).thenReturn(stations);
        final RequestBuilder request = MockMvcRequestBuilders.post("/filter")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        // get the result from controller
        final ModelAndView result = mockMvc.perform(request).andExpect(status().isOk()).andReturn().getModelAndView();

        // assert the view name
        assertEquals("index :: searchRequestErrorState",result.getViewName());
        final List<FieldError> errors = (List<FieldError>) result.getModelMap().get("errors");
        assertEquals(2, errors.size(), "Received 2 errors");
    }

    @Test
    public void test_Search_With_StartDate_NoEndDate() throws Exception {
        final List<StationDto> stations = new ArrayList<StationDto>();
        when(stationService.getAllStations()).thenReturn(stations);
        final RequestBuilder request = MockMvcRequestBuilders.post("/filter")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED).param("startDate", "2020-04-05");
        // get the result from controller
        final ModelAndView result = mockMvc.perform(request).andExpect(status().isOk()).andReturn().getModelAndView();

        // assert the view name
        assertEquals("index :: searchRequestErrorState",result.getViewName());
        // assert the error messages
        final List<FieldError> errors = (List<FieldError>) result.getModelMap().get("errors");
        assertEquals(1, errors.size(), "Received 1 error");
        assertEquals("endDate", errors.get(0).getField(), "Received failed field as endDate");
        assertEquals("End date is required", errors.get(0).getCode(), "Received expected error message");
    }

    @Test
    public void test_Search_With_EndDate_NoStart() throws Exception {
        final List<StationDto> stations = new ArrayList<StationDto>();
        when(stationService.getAllStations()).thenReturn(stations);
        final RequestBuilder request = MockMvcRequestBuilders.post("/filter")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED).param("endDate", "2020-04-05");
        // get the result from controller
        final ModelAndView result = mockMvc.perform(request).andExpect(status().isOk()).andReturn().getModelAndView();

        // assert the view name
        assertEquals("index :: searchRequestErrorState",result.getViewName());
        // assert the error messages
        final List<FieldError> errors = (List<FieldError>) result.getModelMap().get("errors");
        assertEquals(1, errors.size(), "Received 1 error");
        assertEquals("startDate", errors.get(0).getField(), "Received failed field as startDate");
        assertEquals("Start date is required", errors.get(0).getCode(), "Received expected error message");
    }

    @Test
    public void test_Search_With_InvalidDateValue() throws Exception {
        final List<StationDto> stations = new ArrayList<StationDto>();
        when(stationService.getAllStations()).thenReturn(stations);
        final RequestBuilder request = MockMvcRequestBuilders.post("/filter")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED).param("startDate", "test");
        // get the result from controller
        final MvcResult result = mockMvc.perform(request).andExpect(status().isBadRequest()).andReturn();

        // assert bad request status code
        assertEquals(result.getResponse().getStatus(), 400);

    }

    @Test
    public void test_Search_With_InvalidDateRange() throws Exception {
        final List<StationDto> stations = new ArrayList<StationDto>();
        when(stationService.getAllStations()).thenReturn(stations);
        final RequestBuilder request = MockMvcRequestBuilders.post("/filter")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED).param("startDate", "2020-04-05")
                .param("endDate", "2020-04-01");
        // get the result from controller
        final ModelAndView result = mockMvc.perform(request).andExpect(status().isOk()).andReturn().getModelAndView();

        // assert the view name
        assertEquals("index :: searchRequestErrorState",result.getViewName());
        // assert the error messages
        final List<FieldError> errors = (List<FieldError>) result.getModelMap().get("errors");
        assertEquals(1, errors.size(), "Received 1 error");
        assertEquals("endDate", errors.get(0).getField(), "Received failed field as endDate");
        assertEquals("End date must be greater than or equals to Start date", errors.get(0).getCode(),
                "Received expected error message");
    }

    @Test
    public void test_StationDetails_WhenValidStationID_ReturnStation() throws Exception {
        final StationDto station = new StationDto("Station1", "ON",
                new Date(dateFormatter.parse("2020-04-10", Locale.CANADA).getTime()), 30.00, 40.21, 20.19);
        when(stationService.getStationById(anyLong())).thenReturn(station);
        final RequestBuilder request = MockMvcRequestBuilders.get("/station/100");

        // get the result from controller
        final ModelAndView result = mockMvc.perform(request).andExpect(status().isOk()).andReturn().getModelAndView();

        // assert the view name
        assertEquals(result.getViewName(), "station");
        final StationDto stationInResult = (StationDto) result.getModel().get("station");
        assertEquals(station, stationInResult, "Station is retrieved");
        assertEquals(stationInResult.getName(), "Station1", "Expected 1st result as Station1");
    }

    @Test
    public void test_StationDetails_WhenInvalidValidStation_ThrowsNotFoundException() throws Exception {
        when(stationService.getStationById(anyLong())).thenReturn(null);
        final RequestBuilder request = MockMvcRequestBuilders.get("/station/100");

        // get the result from controller
        final MvcResult result = mockMvc.perform(request).andExpect(status().isNotFound()).andReturn();

        // assert not found status code
        assertEquals(result.getResponse().getStatus(), 404);
    }
}