package ru.tsypaev.link.service;

import org.springframework.stereotype.Service;
import ru.tsypaev.link.domain.Link;
import ru.tsypaev.link.repository.LinkRepository;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.hash.Hashing.murmur3_32;


@Service
public class LinkService {

    private final LinkRepository repository;

    public LinkService(LinkRepository repository) {

        this.repository = repository;
    }

    public String getShortLink(String link) {

        Link findLink = repository.findByOriginal(link);

        if (findLink != null) {
            return findLink.getLink();
        }

        String key = murmur3_32().hashString(link, StandardCharsets.UTF_8).toString();
        repository.save(new Link(key, link));

        return key;
    }

    public String redirect(String shortUrl) {

        Link link = repository.findByLink(shortUrl);
        if(link == null) {
            return null; //TODO вернуть статус
        }

        int counter = link.getCount();
        link.setCount(new AtomicInteger(counter).incrementAndGet());

        repository.save(link);

        return link.getOriginal();
    }
}
