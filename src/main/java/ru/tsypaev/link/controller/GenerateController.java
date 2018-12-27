package ru.tsypaev.link.controller;

import org.springframework.web.bind.annotation.*;
import ru.tsypaev.link.service.LinkService;

import java.util.Map;

@RestController
@RequestMapping("/generate")
public class GenerateController {

    private final LinkService linkService;

    public GenerateController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping
    Map<String, String> shorterLink(@RequestBody String url) {
        return linkService.getShortLink(url);
    }
}
