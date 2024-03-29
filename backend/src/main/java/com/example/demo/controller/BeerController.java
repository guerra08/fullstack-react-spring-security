package com.example.demo.controller;

import com.example.demo.contract.BeerResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/beer")
public class BeerController {

    @GetMapping
    public Mono<BeerResponse> getRandomData(){
        return WebClient.create("https://random-data-api.com/api")
            .get().uri("/beer/random_beer")
            .retrieve().bodyToMono(BeerResponse.class);
    }

}
