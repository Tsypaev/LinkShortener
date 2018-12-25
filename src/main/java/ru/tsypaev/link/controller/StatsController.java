package ru.tsypaev.link.controller;

import org.springframework.web.bind.annotation.*;
import ru.tsypaev.link.domain.Link;
import ru.tsypaev.link.repository.LinkRepository;
import ru.tsypaev.link.service.StatsService;

import java.util.stream.Stream;

@RestController
@RequestMapping("/stats")
public class StatsController {

    private final LinkRepository repository;
    private final StatsService statsService;

    public StatsController(LinkRepository repository, StatsService statsService) {
        this.repository = repository;
        this.statsService = statsService;
    }

    @GetMapping(params = { "page", "count" })
    @ResponseBody
    Stream<Link> getAllLinkByPage(@RequestParam( "page" ) int page, @RequestParam( "count" ) int count){ //TODO max size of count = 100
        return statsService.getPageLinks(page, count);
    }

    @GetMapping("/{shortUrl}")
    Link getStatsByShortUrl(@PathVariable String shortUrl) {
        return statsService.getLinkInfo(shortUrl);
    }
}
