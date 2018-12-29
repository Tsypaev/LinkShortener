package ru.tsypaev.link.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.tsypaev.link.domain.Link;
import ru.tsypaev.link.exception.InvalidCountException;
import ru.tsypaev.link.exception.NoDataFoundException;
import ru.tsypaev.link.repository.LinkRepository;

import java.util.stream.Stream;

@Service
public class StatisticService {

    private static final Logger log = LogManager.getLogger(StatisticService.class);

    private final LinkRepository repository;

    public StatisticService(LinkRepository repository) {
        this.repository = repository;
    }

    public Link getLinkInfo(String shortUrl) {

        Link link = repository.findByLink("/l/" + shortUrl);

        if (link == null) {
            log.warn("Can't found link: " + shortUrl);
            throw new NoDataFoundException();
        }

        return link;
    }

    public Stream<Link> getPageLinks(int page, int count) {

        if (count > 100) {
            log.warn("Count have value grater then 100: " + count);
            throw new InvalidCountException();
        }

        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "count"));
        Page<Link> linkList = repository.findAll(new PageRequest(page - 1, count, sort));

        return linkList.get();
    }
}
