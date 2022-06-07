package com.example.exchangerateservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@JsonDeserialize(using = GiphsDeserializer.class)
public class Giphs {
    private Set<Giph> giphs = Collections.synchronizedSet(new HashSet<>());

    public Giphs() {
    }

    public Set<Giph> getGiphs() {
        return giphs;
    }

    public void setGiphs(Set<Giph> giphs) {
        this.giphs = giphs;
    }
}
