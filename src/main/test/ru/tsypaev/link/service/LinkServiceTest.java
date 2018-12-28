package ru.tsypaev.link.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.tsypaev.link.repository.LinkRepository;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LinkServiceTest {

    @Autowired
    LinkRepository linkRepository;

    @Test
    public void createShortLinkTest() {
        LinkService linkService = new LinkService(linkRepository);
        Map<String, String> originalLinkMap = new HashMap<>();
        originalLinkMap.put("original", "https://www.yandex.ru");
        assert linkService.createShortLink(originalLinkMap).get("link").equals("d9c9bc4c");
//        assertThat(linkService.createShortLink(originalLinkMap).equals("d9c9bc4c"));
    }

    void getOriginalByShortUrl() {
    }
}