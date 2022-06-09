package com.example.exchangerateservice.controller;

import com.example.exchangerateservice.model.Giph;
import com.example.exchangerateservice.service.RateServiceImpl;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RateServiceController {

    private final RateServiceImpl rateService;
    private final HttpHeaders headers = new HttpHeaders();

    public RateServiceController(RateServiceImpl rateService) {
        this.rateService = rateService;
        headers.add("Content-Type", "application/json");
    }

    @GetMapping("/currencies/{currencyName}")
    public ResponseEntity<String> getLastCourses(@PathVariable String currencyName) {

        var node = JsonNodeFactory.instance.objectNode();

        try {
            Double lastRate = rateService.getRate(rateService.getCourses(), currencyName);
            Double yesterdayRate = rateService.getRate(rateService.getHistoricalCourses(rateService.getLastDay()), currencyName);

            node.put("last", lastRate);
            node.put("yesterday", yesterdayRate);

            Giph giph;
            if (lastRate < yesterdayRate) {
                giph = rateService.getRandomGif(rateService.getGifs("reach"));
            } else {
                giph = rateService.getRandomGif(rateService.getGifs("broke"));
            }

            node.put("gif", giph.getEmbedUrl());
            return new ResponseEntity<>(node.toString(), headers, HttpStatus.OK);
        } catch (NullPointerException e) {
            throw new NullPointerException("Not Found Currency");
        }
    }

    @ExceptionHandler(NullPointerException.class)
    ResponseEntity<String> handleNullPointerException(NullPointerException e) {
        var node = JsonNodeFactory.instance.objectNode();
        node.put("status", HttpStatus.NOT_FOUND.value());
        node.put("error", "Not Found");
        node.put("message", e.getMessage());
        return new ResponseEntity<>(node.toString(), headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<String> handleRuntime(RuntimeException e) {
        var node = JsonNodeFactory.instance.objectNode();
        node.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        node.put("error", "Server Error");
        node.put("message", e.getMessage());
        return new ResponseEntity<>(node.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


