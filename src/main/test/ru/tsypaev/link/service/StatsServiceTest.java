package ru.tsypaev.link.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.tsypaev.link.domain.Link;
import ru.tsypaev.link.exception.NoDataFoundException;
import ru.tsypaev.link.repository.LinkRepository;

import javax.persistence.EntityManager;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StatsServiceTest {

    @Autowired
    LinkRepository linkRepository;

    @Autowired
    EntityManager entityManager;

    private static final String YANDEX_URL = "https://www.yandex.ru";
    private static final String RAMBLER_URL = "https://www.rambler.ru";
    private static final String GOOGLE_URL = "https://www.google.com";
    private static final String YANDEX_LINK = "12345";
    private static final String RAMBLER_LINK = "123456";
    private static final String GOOGLE_LINK = "1234567";
    private static final String ORIGINAL = "https://www.yandex.ru";
    private static final String NOT_ORIGINAL = "https://www.google.com";
    private static final String LINK = "123456";
    private static final int COUNT = 256;
    private static final int RANK = 128;
    private static final int PAGE_VALUE = 0;
    private static final int COUNT_VALUE = 2;

    @Test
    public void shouldReturnLinkInfo() {
        StatsService statsService = new StatsService(linkRepository);
        Link link = new Link(LINK, ORIGINAL, RANK, COUNT);
        entityManager.persist(link);

        Link linkInfo = statsService.getLinkInfo(LINK);

        assertThat(linkInfo.getLink()).isEqualTo(LINK);
        assertThat(linkInfo.getOriginal()).isEqualTo(ORIGINAL);
        assertThat(linkInfo.getRank()).isEqualTo(RANK);
        assertThat(linkInfo.getCount()).isEqualTo(COUNT);
    }

    @Test(expected = NoDataFoundException.class)
    public void shouldThrowNoDataFoundException() {
        StatsService statsService = new StatsService(linkRepository);
        Link link = new Link(LINK, ORIGINAL, RANK, COUNT);
        entityManager.persist(link);

        statsService.getLinkInfo(NOT_ORIGINAL);
    }

    @Test
    public void shouldReturnPageLinks() {
        StatsService statsService = new StatsService(linkRepository);

        Link link1 = new Link(YANDEX_LINK, YANDEX_URL,1,2);
        Link link2 = new Link(RAMBLER_LINK, GOOGLE_URL,2,3);
        Link link3 = new Link(GOOGLE_LINK, RAMBLER_URL,3,1);

        entityManager.persist(link1);
        entityManager.persist(link2);
        entityManager.persist(link3);

        Stream<Link> pageLinks = statsService.getPageLinks(PAGE_VALUE, COUNT_VALUE);
        assertThat(pageLinks.count()).isEqualTo(COUNT_VALUE);
    }

    @Test
    public void shouldReturnPageLinks1() {
        StatsService statsService = new StatsService(linkRepository);

        Link link1 = new Link(YANDEX_LINK, YANDEX_URL,1,2);
        Link link2 = new Link(RAMBLER_LINK, GOOGLE_URL,2,3);
        Link link3 = new Link(GOOGLE_LINK, RAMBLER_URL,3,1);

        entityManager.persist(link1);
        entityManager.persist(link2);
        entityManager.persist(link3);

        Stream<Link> pageLinks = statsService.getPageLinks(PAGE_VALUE, COUNT_VALUE);
        Object[] objects = pageLinks.toArray();

        assertThat(objects[0]).isEqualTo(link2);
        assertThat(objects[1]).isEqualTo(link1);

    }
}