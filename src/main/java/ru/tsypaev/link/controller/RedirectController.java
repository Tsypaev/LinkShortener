package ru.tsypaev.link.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tsypaev.link.service.LinkService;

import java.net.URISyntaxException;

@RestController
@RequestMapping
public class RedirectController {

    private final LinkService linkService;

    public RedirectController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/l/{shortUrl}")
    ResponseEntity getFullLink(@PathVariable String shortUrl) throws URISyntaxException {
        return ResponseEntity.status(HttpStatus.FOUND).location(linkService.redirect(shortUrl)).build();
    }
}
