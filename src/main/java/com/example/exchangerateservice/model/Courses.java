package com.example.exchangerateservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@JsonDeserialize(using = CoursesDeserializer.class)
public class Courses {

    private Map<String, Double> rates = new ConcurrentHashMap<>();

    public Courses() {

    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
