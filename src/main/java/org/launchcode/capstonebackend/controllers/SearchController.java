package org.launchcode.capstonebackend.controllers;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@RestController
@RequestMapping("search")
public class SearchController {
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.themoviedb.org/3/search/keyword?page=1"))
            .header("accept", "application/json")
            .header("Authorization", "Bearer ede652a83ec90771e7ded8aa02c202da")
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build();
    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

    public SearchController() throws IOException, InterruptedException {
    }

    @GetMapping
    public String search(Model model) {
        return "search";
    }

    @PostMapping
    public String displaySearchResults(Model model, @RequestParam String searchTerm) {

        return "search";
    }

}
