package com.example.exchangerateservice.service;

import com.example.exchangerateservice.utils.CourseApiDateConverter;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

@Service
public class RateService {

    private final CourseService courseService;
    private final GiphyService giphyService;
    private final CourseApiDateConverter courseApiDateConverter;

    public RateService(CourseService courseService, GiphyService giphyService, CourseApiDateConverter courseApiDateConverter) {
        this.courseService = courseService;
        this.giphyService = giphyService;
        this.courseApiDateConverter = courseApiDateConverter;
    }

    public ObjectNode getCourse(String currencyName) {
        var node = JsonNodeFactory.instance.objectNode();

        try {
            Double lastRate = courseService.getRate(courseService.getCourses(), currencyName);
            Double yesterdayRate = courseService.getRate(courseService.getHistoricalCourses(courseApiDateConverter.getLastDay()), currencyName);
            String gif = giphyService.getGifByCompare(lastRate, yesterdayRate).getEmbedUrl();

            node.put("last", lastRate);
            node.put("yesterday", yesterdayRate);
            node.put("gif", gif);
        } catch (NullPointerException e) {
            throw new NullPointerException("Not Found Currency");
        }
        return node;
    }
}
