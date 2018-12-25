package ru.tsypaev.link.service;

import org.springframework.stereotype.Service;
import ru.tsypaev.link.domain.Link;
import ru.tsypaev.link.repository.LinkRepository;

@Service
public class StatsService {

    private final LinkRepository repository;

    public StatsService(LinkRepository repository) {
        this.repository = repository;
    }

    public Link getLinkInfo(String shortUrl) {

        return repository.findByLink(shortUrl);
    }
}
