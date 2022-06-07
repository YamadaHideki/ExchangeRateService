package com.example.exchangerateservice.model;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

public class CoursesDeserializer extends JsonDeserializer<Courses> {

    @Override
    public Courses deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        Courses courses = new Courses();
        ObjectMapper mapper = new ObjectMapper();
        var node = (JsonNode) mapper.readTree(p);
        var rates = node.get("rates");
        var keys = rates.fieldNames();

        var ratesMap = courses.getRates();
        while (keys.hasNext()) {
            String key = keys.next();
            double value = rates.get(key).asDouble();
            ratesMap.put(key, value);
        }

        return courses;
    }
}
