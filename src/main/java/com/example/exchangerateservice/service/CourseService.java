package com.example.exchangerateservice.service;

import com.example.exchangerateservice.client.CourseApiClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CourseService {

    private final CourseApiClient courseApiClient;

    public CourseService(CourseApiClient courseApiClient) {
        this.courseApiClient = courseApiClient;
    }

    public Map<String, Double> getCourses() {
        return courseApiClient.getLastCourses().getRates();
    }

    public Map<String, Double> getHistoricalCourses(String date) {
        return courseApiClient.getHistoricalCourses(date).getRates();
    }

    public Double getRate(Map<String, Double> map, String currencyName) {
        return map.get(currencyName.toUpperCase());
    }

}
