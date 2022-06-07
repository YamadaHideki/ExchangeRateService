package com.example.exchangerateservice;

import com.example.exchangerateservice.client.CourseApiClient;
import com.example.exchangerateservice.client.GiphyApiClient;
import com.example.exchangerateservice.controller.RateServiceController;
import com.example.exchangerateservice.model.Courses;
import com.example.exchangerateservice.model.Giphs;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class ExchangeRateServiceApplicationTests {

    @MockBean
    CourseApiClient courseApiClient;

    @MockBean
    GiphyApiClient giphyApiClient;

    @Autowired
    RateServiceController rateServiceController;

    private static Courses courses;
    private static Courses coursesHistory;
    private static Giphs giphs;

    @BeforeAll
    static void before() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            courses = mapper.readValue(
                    new File(ExchangeRateServiceApplicationTests.class.getClassLoader().getResource("open-exchange.json").toURI()), Courses.class);
            giphs = mapper.readValue(
                    new File(ExchangeRateServiceApplicationTests.class.getClassLoader().getResource("giphs.json").toURI()), Giphs.class);
            coursesHistory = mapper.readValue(
                    new File(ExchangeRateServiceApplicationTests.class.getClassLoader().getResource("open-exchange-history.json").toURI()), Courses.class);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void contextLoads() {
    }

    @Test
    void rateServiceControllerTest() throws JsonProcessingException {
        when(courseApiClient.getLastCourses()).thenReturn(courses);
        when(courseApiClient.getHistoricalCourses(anyString())).thenReturn(coursesHistory);
        when(giphyApiClient.searchGif(anyString())).thenReturn(giphs);

        ResponseEntity<String> responseEntity = rateServiceController.getLastCourses("AMD");

        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());

        ObjectMapper mapper = new ObjectMapper();
        var node = mapper.readTree(responseEntity.getBody());

        assertThat(node.get("last").asDouble()).isInstanceOf(Double.class);
        assertThat(node.get("yesterday").asDouble()).isInstanceOf(Double.class);
        assertThat(node.get("gif").asText()).isInstanceOf(String.class);
    }
}
