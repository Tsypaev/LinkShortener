package ru.tsypaev.link.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.tsypaev.link.domain.Link;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LinkRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    LinkRepository linkRepository;

    private static final String YANDEX_URL = "https://www.yandex.ru";
    private static final String RAMBLER_URL = "https://www.rambler.ru";
    private static final String GOOGLE_URL = "https://www.google.com";
    private static final String YANDEX_LINK = "12345";
    private static final String RAMBLER_LINK = "123456";
    private static final String GOOGLE_LINK = "1234567";

    @Test
    public void findByLinkShouldFindLinkByShortUrl() {
        entityManager.persist(new Link(YANDEX_LINK, YANDEX_URL));

        Link link = linkRepository.findByLink(YANDEX_LINK);
        assertThat(link.getOriginal()).isEqualTo(YANDEX_URL);
    }

    @Test
    public void findByOriginalShouldFindLinkByOriginalUrl() {
        entityManager.persist(new Link(YANDEX_LINK, YANDEX_URL));

        Link link = linkRepository.findByOriginal(YANDEX_URL);
        assertThat(link.getLink()).isEqualTo(YANDEX_LINK);
    }

    @Test
    public void findAllByOrderByCountDescShouldFindAllLinksOrderedByCountDesc() {
        Link link1 = new Link(YANDEX_LINK, YANDEX_URL, 1, 2);
        Link link2 = new Link(RAMBLER_LINK, GOOGLE_URL, 2, 3);
        Link link3 = new Link(GOOGLE_LINK, RAMBLER_URL, 3, 1);

        entityManager.persist(link1);
        entityManager.persist(link2);
        entityManager.persist(link3);

        List<Link> links = linkRepository.findAllByOrderByCountDesc();

        assertThat(links.get(0)).isEqualTo(link2);
        assertThat(links.get(1)).isEqualTo(link1);
        assertThat(links.get(2)).isEqualTo(link3);
    }

}