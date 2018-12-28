package ru.tsypaev.link.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.tsypaev.link.domain.Link;
import ru.tsypaev.link.repository.LinkRepository;
import ru.tsypaev.link.service.LinkService;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RedirectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LinkService linkService;

    @Autowired
    private LinkRepository repository;

    @Autowired
    EntityManager entityManager;

    @Before
    public void setup() throws URISyntaxException {

        entityManager.persist(new Link("d9c9bc4c","https://www.yandex.ru"));
        this.mockMvc = standaloneSetup(new GenerateController(linkService)).build();
//        Mockito.when(repository.findByLink("d9c9bc4c")).thenReturn(new Link("d9c9bc4c","https://www.yandex.ru"));
    }

    @Test
    public void shouldRedirect() throws Exception {
        LinkService mock = org.mockito.Mockito.mock(LinkService.class);
        Mockito.when(mock.getOriginalByShortUrl("d9c9bc4c")).thenReturn(new URI("https://www.yandex.ru"));
        this.mockMvc.perform(
                get("/l/d9c9bc4c")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isFound())
                .andExpect(content().string("d"));
    }
}