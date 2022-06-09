package com.example.exchangerateservice.service;

import com.example.exchangerateservice.client.CourseApiClient;
import com.example.exchangerateservice.client.GiphyApiClient;
import com.example.exchangerateservice.model.Giph;
import com.example.exchangerateservice.model.Giphs;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

@Service
public class RateServiceImpl implements RateService {

    private final CourseApiClient courseApiClient;
    private final GiphyApiClient giphyApiClient;

    public RateServiceImpl(CourseApiClient courseApiClient, GiphyApiClient giphyApiClient) {
        this.courseApiClient = courseApiClient;
        this.giphyApiClient = giphyApiClient;
    }

    @Override
    public Map<String, Double> getCourses() {
        return courseApiClient.getLastCourses().getRates();
    }

    @Override
    public Map<String, Double> getHistoricalCourses(String date) {
        return courseApiClient.getHistoricalCourses(date).getRates();
    }

    @Override
    public Double getRate(Map<String, Double> map, String currencyName) {
        return map.get(currencyName.toUpperCase());
    }

    @Override
    public Giphs getGifs(String name) {
        return giphyApiClient.searchGif(name);
    }

    @Override
    public String getLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.add(Calendar.DATE, -1);
        return getDayInFormat(new SimpleDateFormat("yyyy-MM-dd"), calendar);
    }

    @Override
    public String getDayInFormat(SimpleDateFormat simpleDateFormat, Calendar calendar) {
        return simpleDateFormat.format(calendar.getTime());
    }

    @Override
    public Giph getRandomGif(Giphs giphs) {
        var set = giphs.getGiphs();
        int item = new Random().nextInt(set.size());
        int i = 0;
        for (Giph giph : set) {
            if (i == item) {
                return giph;
            }
            i++;
        }
        return null;
    }
}
