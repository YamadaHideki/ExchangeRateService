package com.example.exchangerateservice.controller;

import com.example.exchangerateservice.client.CourseApiClient;
import com.example.exchangerateservice.client.GiphyApiClient;
import com.example.exchangerateservice.model.Giph;
import com.example.exchangerateservice.model.Giphs;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

@RestController
@RequestMapping("/api")
public class RateServiceController {

    private final CourseApiClient courseApiClient;
    private final GiphyApiClient giphyApiClient;
    private final HttpHeaders headers = new HttpHeaders();

    public RateServiceController(CourseApiClient courseApiClient, GiphyApiClient giphyApiClient) {
        this.courseApiClient = courseApiClient;
        this.giphyApiClient = giphyApiClient;
        headers.add("Content-Type", "application/json");
    }

    @GetMapping("/currencies/{currencyName}")
    public ResponseEntity<String> getLastCourses(@PathVariable String currencyName) {

        var courses = courseApiClient.getLastCourses().getRates();

        String lastDay = getLastDayInFormat(new SimpleDateFormat("yyyy-MM-dd"), TimeZone.getTimeZone("UTC"));
        var historicalCourses = courseApiClient.getHistoricalCourses(lastDay).getRates();

        try {
            Double lastRate = courses.get(currencyName.toUpperCase());
            Double oneDayAgoRate = historicalCourses.get(currencyName.toUpperCase());

            var node = JsonNodeFactory.instance.objectNode();
            node.put("last", lastRate);
            node.put("yesterday", oneDayAgoRate);

            Giphs giphs;
            if (lastRate < oneDayAgoRate) {
                giphs = giphyApiClient.searchGif("reach");
                Giph giph = getRandomGif(giphs);
                node.put("gif", giph.getEmbedUrl());
            } else {
                giphs = giphyApiClient.searchGif("broke");
                Giph giph = getRandomGif(giphs);
                node.put("gif", giph.getEmbedUrl());
            }
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

    public String getLastDayInFormat(SimpleDateFormat simpleDateFormat, TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(timeZone);
        calendar.add(Calendar.DATE, -1);

        return simpleDateFormat.format(calendar.getTime());
    }

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


