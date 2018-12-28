package ru.tsypaev.link.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.tsypaev.link.domain.Link;
import ru.tsypaev.link.exception.ExistInDbException;
import ru.tsypaev.link.exception.InvalidUrlException;
import ru.tsypaev.link.exception.NoDataFoundException;
import ru.tsypaev.link.repository.LinkRepository;

import javax.persistence.EntityManager;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LinkServiceTest {

    @Autowired
    LinkRepository linkRepository;

    @Autowired
    EntityManager entityManager;

    private static final String CORRECT_URL = "https://www.yandex.ru";
    private static final String INCORRECT_URL = "htps://www.yandex.ru";
    private static final String LINK = "12345";

    @Test
    public void shouldCreateShortLink() {
        LinkService linkService = new LinkService(linkRepository);
        Map<String, String> originalLinkMap = new HashMap<>();
        originalLinkMap.put("original", CORRECT_URL);
        assertThat(linkService.createShortLink(originalLinkMap).get("link")).isEqualTo("/l/d9c9bc4c");
    }

    @Test(expected = InvalidUrlException.class)
    public void shouldThrowInvalidUrlException() {
        LinkService linkService = new LinkService(linkRepository);
        Map<String, String> originalLinkMap = new HashMap<>();
        originalLinkMap.put("original", INCORRECT_URL);
        linkService.createShortLink(originalLinkMap);
    }

    @Test(expected = ExistInDbException.class)
    public void shouldThrowExistInDbException() {
        entityManager.persist(new Link(LINK, CORRECT_URL));
        LinkService linkService = new LinkService(linkRepository);
        Map<String, String> originalLinkMap = new HashMap<>();
        originalLinkMap.put("original", CORRECT_URL);
        linkService.createShortLink(originalLinkMap);
    }

    @Test
    public void shouldGetOriginalByShortUrl() throws URISyntaxException {
        LinkService linkService = new LinkService(linkRepository);
        entityManager.persist(new Link(LINK, CORRECT_URL));
        assertThat(linkService.getOriginalByShortUrl(LINK).toString()).isEqualTo(CORRECT_URL);
    }

    @Test
    public void shouldGetAndIncrement() throws URISyntaxException {
        LinkService linkService = new LinkService(linkRepository);
        int count = 0;
        Link link = new Link(LINK, CORRECT_URL, 0, count);
        entityManager.persist(link);
        linkService.getOriginalByShortUrl(LINK);
        assertThat(link.getCount()).isEqualTo(++count);
    }

    @Test(expected = NoDataFoundException.class)
    public void shouldThrowNoDataFoundException() throws URISyntaxException {
        LinkService linkService = new LinkService(linkRepository);
        entityManager.persist(new Link(LINK, CORRECT_URL));
        linkService.getOriginalByShortUrl(INCORRECT_URL);

    }
}