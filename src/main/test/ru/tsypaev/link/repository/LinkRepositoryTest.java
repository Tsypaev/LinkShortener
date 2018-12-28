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

    @Test
    public void ShouldFindLinkByShortUrl(){
        entityManager.persist(new Link("12345", "https://www.yandex.ru"));

        Link link = linkRepository.findByLink("12345");
        assertThat(link.getOriginal()).isEqualTo("https://www.yandex.ru");
    }

    @Test
    public void ShouldFindLinkByOriginalUrl(){
        entityManager.persist(new Link("12345", "https://www.yandex.ru"));

        Link link = linkRepository.findByOriginal("https://www.yandex.ru");
        assertThat(link.getLink()).isEqualTo("12345");
    }

    @Test
    public void ShouldFindAllLinksOrderedByCountDesc(){

        Link link1 = new Link("12345", "https://www.yandex.ru",1,2);
        Link link2 = new Link("123456", "https://www.google.com",2,3);
        Link link3 = new Link("1234567", "https://www.rambler.ru",3,1);

        entityManager.persist(link1);
        entityManager.persist(link2);
        entityManager.persist(link3);

        List<Link> links = linkRepository.findAllByOrderByCountDesc();

        assertThat(links.get(0)).isEqualTo(link2);
        assertThat(links.get(1)).isEqualTo(link1);
        assertThat(links.get(2)).isEqualTo(link3);
    }

}