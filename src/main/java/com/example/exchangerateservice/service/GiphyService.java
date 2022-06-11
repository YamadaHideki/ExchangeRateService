package com.example.exchangerateservice.service;

import com.example.exchangerateservice.client.GiphyApiClient;
import com.example.exchangerateservice.model.Giph;
import com.example.exchangerateservice.model.Giphs;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GiphyService {

    private final GiphyApiClient giphyApiClient;

    public GiphyService(GiphyApiClient giphyApiClient) {
        this.giphyApiClient = giphyApiClient;
    }

    public Giphs getGifs(String name) {
        return giphyApiClient.searchGif(name);
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

    public Giph getGifByCompare(Double a, Double b) {
        return a < b ? getRandomGif(getGifs("reach")) : getRandomGif(getGifs("broke"));
    }
}
