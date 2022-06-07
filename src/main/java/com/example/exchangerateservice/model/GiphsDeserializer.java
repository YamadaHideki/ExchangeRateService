package com.example.exchangerateservice.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class GiphsDeserializer extends JsonDeserializer<Giphs> {
    @Override
    public Giphs deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Giphs giphs = new Giphs();
        var setGiphs = giphs.getGiphs();

        ObjectMapper mapper = new ObjectMapper();
        var node = (JsonNode) mapper.readTree(p);

        for (JsonNode jsonNode : node.get("data")) {
            Giph giph = new Giph();
            giph.setEmbedUrl(jsonNode.get("embed_url").asText());
            setGiphs.add(giph);
        }

        return giphs;
    }
}
