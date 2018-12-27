package ru.tsypaev.link.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tsypaev.link.service.LinkService;

@RestController
@RequestMapping
public class RedirectController {

    private final LinkService linkService;

    public RedirectController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/l/{shortUrl}")
    String getFullLink(@PathVariable String shortUrl) {
        return linkService.redirect(shortUrl);
    }
}
