package ru.tsypaev.link.controller;

import org.springframework.web.bind.annotation.*;
import ru.tsypaev.link.service.LinkService;

@RestController
@RequestMapping("/generate")
public class GenerateController {

    private final LinkService linkService;

    public GenerateController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping
    String shorterLink(@RequestBody String url) {
        return linkService.getShortLink(url);
    }

    @GetMapping("/l/{shortUrl}")
    String getFullLink(@PathVariable String shortUrl) {
        return linkService.redirect(shortUrl);
    }
}
