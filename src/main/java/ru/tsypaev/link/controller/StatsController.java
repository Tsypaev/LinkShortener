package ru.tsypaev.link.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tsypaev.link.domain.Link;
import ru.tsypaev.link.service.StatsService;

@RestController
@RequestMapping("/stats")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/{shortUrl}")
    Link getStatsByShortUrl(@PathVariable String shortUrl) {

        statsService.getLinkInfo(shortUrl);

        return statsService.getLinkInfo(shortUrl);
    }
}
