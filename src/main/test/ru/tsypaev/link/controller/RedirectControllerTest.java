package ru.tsypaev.link.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.tsypaev.link.service.LinkService;

import java.net.URI;

import static org.springframework.http.HttpStatus.FOUND;

@RunWith(SpringRunner.class)
@WebMvcTest(RedirectController.class)
public class RedirectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LinkService linkService;

    private static final String YANDEX_URL = "https://www.yandex.ru";
    private static final String YANDEX_LINK = "d9c9bc4c";

    @Test
    public void redirectControllerShouldRedirectOnLink() throws Exception {
        Mockito.when(linkService.getOriginalByShortUrl(YANDEX_LINK)).thenReturn(new URI(YANDEX_URL));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/l/" + YANDEX_LINK)
                .accept(MediaType.APPLICATION_JSON);

        int status = mockMvc.perform(requestBuilder).andReturn().getResponse().getStatus();
        assert status == FOUND.value();

    }
}