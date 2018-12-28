package ru.tsypaev.link.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.tsypaev.link.domain.Link;
import ru.tsypaev.link.exception.NoDataFoundException;
import ru.tsypaev.link.repository.LinkRepository;

import java.util.stream.Stream;

@Service
public class StatsService {

    private static final Logger log = LogManager.getLogger(StatsService.class);

    private final LinkRepository repository;

    public StatsService(LinkRepository repository) {
        this.repository = repository;
    }

    public Link getLinkInfo(String shortUrl) {

        Link byLink = repository.findByLink(shortUrl);

        if (byLink == null) {
            log.warn("Can't found link: " + shortUrl);
            throw new NoDataFoundException();
        }

        return byLink;
    }

    public Stream<Link> getPageLinks(int page, int count){
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "count"));
        Page<Link> clientlist = repository.findAll(new PageRequest(page, count, sort));
        Stream<Link> linkStream = clientlist.get();
        return linkStream;
    }
}
