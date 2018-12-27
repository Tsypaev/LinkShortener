package ru.tsypaev.link.service;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import ru.tsypaev.link.domain.Link;
import ru.tsypaev.link.exception.ExistInDbException;
import ru.tsypaev.link.exception.InvalidUrlException;
import ru.tsypaev.link.exception.NoDataFoundException;
import ru.tsypaev.link.repository.LinkRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.hash.Hashing.murmur3_32;


@Service
public class LinkService {

    private final LinkRepository repository;

    public LinkService(LinkRepository repository) {

        this.repository = repository;
    }

    public Map<String, String> getShortLink(Map<String, String> url) {

        String link = url.get("original");

        if (!link.startsWith("http://") && !link.startsWith("https://")) {
            throw new InvalidUrlException();
        }

        Link findLink = repository.findByOriginal(link);

        if (findLink != null) {
            throw new ExistInDbException();
        }

        Map<String, String> genLinks = new HashMap<>();

        String key = murmur3_32().hashString(link, StandardCharsets.UTF_8).toString();

        repository.save(new Link(key, link));
        setRanks();

        String shortUrl = createUri(key);
        genLinks.put("link", shortUrl);

        return genLinks;
    }

    private String createUri(String key) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("key", key);
        UriTemplate template = new UriTemplate("/l/{key}");
        return template.expand(uriVariables).toString();
    }

    public URI redirect(String shortUrl) throws URISyntaxException {

        Link link = repository.findByLink(shortUrl);
        if(link == null) {
            throw new NoDataFoundException();
        }

        int counter = link.getCount();
        link.setCount(new AtomicInteger(counter).incrementAndGet());

        repository.save(link);
        setRanks();

        return new URI(link.getOriginal());
    }

    private void setRanks(){
        Link[] orderList = getOrderList().toArray(new Link[0]);
        for (int i = 0; i < orderList.length; i++) {
            orderList[i].setRank((long) i+1);
            repository.save(orderList[i]);
        }
    }

    private List<Link> getOrderList() {
        return repository.findAllByOrderByCountDesc();
    }
}
