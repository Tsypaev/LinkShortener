package ru.tsypaev.link.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import ru.tsypaev.link.domain.Link;
import ru.tsypaev.link.exception.InvalidUrlException;
import ru.tsypaev.link.exception.NoDataFoundException;
import ru.tsypaev.link.repository.LinkRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.hash.Hashing.murmur3_32;


@Service
public class LinkService {

    private static final Logger log = LogManager.getLogger(LinkService.class);

    private final LinkRepository repository;

    public LinkService(LinkRepository repository) {

        this.repository = repository;
    }

    public Map<String, String> createShortLink(Map<String, String> originalLinkMap) {

        String originalLink = originalLinkMap.get("original");

        if (!originalLink.startsWith("http://") && !originalLink.startsWith("https://")) {
            log.warn("Invalid original link: " + originalLink);
            throw new InvalidUrlException();
        }

        Link originalLinkFromDb = repository.findByOriginal(originalLink);

        Map<String, String> shortLinkMap = new HashMap<>();

        if (originalLinkFromDb != null) {
            log.warn("This link already exist in DB: " + originalLinkFromDb.getOriginal());
            shortLinkMap.put("link", originalLinkFromDb.getLink());
            return shortLinkMap;
        }

        String shortUrl = murmur3_32().hashString(originalLink, StandardCharsets.UTF_8).toString();
        log.debug("Short url was generated: " + shortUrl);


        String shortLink = createUri(shortUrl);
        shortLinkMap.put("link", shortLink);

        repository.save(new Link(shortLink, originalLink));
        log.debug("Short url was save in DB");
        updateRanks();
        log.debug("Ranks was updated");

        return shortLinkMap;
    }

    private String createUri(String shortUrl) {

        Map<String, String> uriMap = new HashMap<>();

        uriMap.put("uri", shortUrl);
        UriTemplate template = new UriTemplate("/l/{uri}");

        return template.expand(uriMap).toString();
    }

    public URI getOriginalByShortUrl(String shortUrl) throws URISyntaxException {

        Link link = repository.findByLink("/l/" + shortUrl);

        if (link == null) {
            log.warn("Can't find original link by this short link: " + shortUrl);
            throw new NoDataFoundException();
        }

        incrementCount(shortUrl, link);

        repository.save(link);
        updateRanks();
        log.debug("Ranks was updated");

        return new URI(link.getOriginal());
    }

    private synchronized void incrementCount(String shortUrl, Link link) {
        int counter = link.getCount();
        link.setCount(++counter);
        log.debug("Increment count for link: " + shortUrl);
    }

    private void updateRanks() {

        Link[] orderLinkList = getOrderLinkList().toArray(new Link[0]);

        for (int rownum = 0; rownum < orderLinkList.length; rownum++) {
            orderLinkList[rownum].setRank(rownum + 1);
            repository.save(orderLinkList[rownum]);
        }
    }

    private List<Link> getOrderLinkList() {
        return repository.findAllByOrderByCountDesc();
    }
}
