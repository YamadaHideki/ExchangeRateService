package com.example.exchangerateservice.service;

import com.example.exchangerateservice.model.Giph;
import com.example.exchangerateservice.model.Giphs;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

public interface RateService {
    Map<String, Double> getCourses();

    Map<String, Double> getHistoricalCourses(String date);

    Double getRate(Map<String, Double> map, String courseName);

    String getLastDay();

    String getDayInFormat(SimpleDateFormat simpleDateFormat, Calendar calendar);

    Giph getRandomGif(Giphs giphs);

    Giphs getGifs(String name);
}
