package com.example.exchangerateservice.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

@Component
public class CourseApiDateConverter {

    public String getLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.add(Calendar.DATE, -1);
        return getDayInFormat(new SimpleDateFormat("yyyy-MM-dd"), calendar);
    }

    public String getDayInFormat(SimpleDateFormat simpleDateFormat, Calendar calendar) {
        return simpleDateFormat.format(calendar.getTime());
    }
}
