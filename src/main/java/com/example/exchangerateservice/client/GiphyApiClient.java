package com.example.exchangerateservice.client;

import com.example.exchangerateservice.model.Giphs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${application.config.gif-api.name}", url = "${application.config.gif-api.url}")
public interface GiphyApiClient {

    @GetMapping("/gifs/search?api_key=" + "${application.config.gif-api.token}" + "&q={searchQuery}")
    Giphs searchGif(@PathVariable String searchQuery);
}
