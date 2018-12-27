package ru.tsypaev.link.service;

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

    private final LinkRepository repository;

    public StatsService(LinkRepository repository) {
        this.repository = repository;
    }

    public Link getLinkInfo(String shortUrl) {

        Link byLink = repository.findByLink(shortUrl);

        if (byLink == null) {
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
