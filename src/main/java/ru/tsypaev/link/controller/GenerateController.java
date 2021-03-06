package ru.tsypaev.link.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    Map<String, String> getShortLink(@RequestBody Map<String, String> url) {
        return linkService.createShortLink(url);
    }
}
