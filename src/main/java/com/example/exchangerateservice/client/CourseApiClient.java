package com.example.exchangerateservice.client;

import com.example.exchangerateservice.model.Courses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "${application.config.course-api.name}", url = "${application.config.course-api.url}")
public interface CourseApiClient {

    @GetMapping("/latest.json?app_id=" + "${application.config.course-api.token}" + "&base=" + "${application.config.course-api.base-course}")
    Courses getLastCourses();

    /**
     * example data: "yyyy-MM-dd"
     */
    @GetMapping("/historical/{data}.json?app_id=" + "${application.config.course-api.token}" + "&base=" + "${application.config.course-api.base-course}")
    Courses getHistoricalCourses(@PathVariable String data);
}
