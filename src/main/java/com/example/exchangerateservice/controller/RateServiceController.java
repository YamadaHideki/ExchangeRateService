package com.example.exchangerateservice.controller;

import com.example.exchangerateservice.service.RateService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RateServiceController {

    private final RateService rateService;
    private final HttpHeaders headers = new HttpHeaders();

    public RateServiceController(RateService rateService) {
        this.rateService = rateService;
        headers.add("Content-Type", "application/json");
    }

    @GetMapping("/currencies/{currencyName}")
    public ResponseEntity<String> getCourse(@PathVariable String currencyName) {
        return new ResponseEntity<>(rateService.getCourse(currencyName).toString(), headers, HttpStatus.OK);
    }
}


