package ru.tsypaev.link.controller;

import org.springframework.web.bind.annotation.*;
import ru.tsypaev.link.domain.Link;
import ru.tsypaev.link.service.StatisticService;

import java.util.stream.Stream;

@RestController
@RequestMapping("/stats")
public class StatisticController {

    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping(params = {"page", "count"})
    Stream<Link> getPaginationList(@RequestParam("page") int page, @RequestParam("count") int count) {
        return statisticService.getPageLinks(page, count);
    }

    @GetMapping("/{shortUrl}")
    Link getStatisticByShortUrl(@PathVariable String shortUrl) {
        return statisticService.getLinkInfo(shortUrl);
    }
}
