package org.launchcode.capstonebackend.controllers;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("search")
public class SearchController {

    @GetMapping
    public String search(Model model) {
        return "search";
    }

    @PostMapping
    public String displaySearchResults(Model model, @RequestParam String searchTerm) {

        return "search";
    }

}
