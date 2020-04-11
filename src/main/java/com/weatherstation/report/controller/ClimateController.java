package com.weatherstation.report.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.weatherstation.report.dto.SearchRequestDto;
import com.weatherstation.report.dto.StationDto;
import com.weatherstation.report.service.StationService;
import com.weatherstation.report.validator.SearchValidator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
public class ClimateController {

    @Autowired
    StationService stationService;

    private static final Logger logger = LogManager.getLogger(ClimateController.class);

    @GetMapping("/")
    public String home(final Model model) {
        logger.debug("initialize index page");
        final List<StationDto> stations = stationService.getAllStations();
        logger.debug("retrieved stations, count # {}", stations.size());
        model.addAttribute("stations", stations);
        model.addAttribute("searchRequest", new SearchRequestDto());
        return "index";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String filter(final SearchRequestDto searchRequest, final RedirectAttributes attribute, final BindingResult result,
            final Model model) {
        logger.debug("received filter request {}", searchRequest);
        final SearchValidator searchValidator = new SearchValidator();
        searchValidator.validate(searchRequest, result);
        logger.debug("is valid request? {}", result.hasErrors());
        if (result.hasErrors()) {
            logger.debug("request dates are not valid, {}", result.getAllErrors());
            model.addAttribute("searchRequest", searchRequest);
            model.addAttribute("errors", result.getAllErrors());
            return "index";
        }
        logger.debug("searching for the given dates {} to {}", searchRequest.getStartDate(),
                searchRequest.getEndDate());
        final List<StationDto> stations = stationService.searchStations(searchRequest.getStartDate().getTime(),
                searchRequest.getEndDate().getTime());
        logger.debug("search result count # {}", stations.size());
        attribute.addFlashAttribute("searchRequest", searchRequest);
        attribute.addFlashAttribute("stations", stations);
        return "redirect:/result";
    }

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public String result(final SearchRequestDto home) {
        return "index";
    }

    @GetMapping("/station/{id}")
    public String getStationDetails(@PathVariable(value = "id") final Long id, final Model model) {
        logger.debug("retrieved station request, ID #{}", id);
        final StationDto station = stationService.getStationById(id);
        if (station == null) {
            logger.error("retrieved no station for the given ID {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Station not found");
        }
        model.addAttribute("station", station);
        return "station";
    }
}