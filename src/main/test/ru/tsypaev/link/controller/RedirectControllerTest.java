package ru.tsypaev.link.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.tsypaev.link.domain.Link;
import ru.tsypaev.link.service.LinkService;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
    EntityManager entityManager;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(new GenerateController(linkService)).build();
    }

    @Test
    public void shouldRedirect() throws Exception {
        entityManager.persist(new Link("d9c9bc4c","https://www.yandex.ru"));
        this.mockMvc.perform(
                get("/l/d9c9bc4c")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isFound())
                .andExpect(content().string("d"));
    }
}