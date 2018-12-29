package ru.tsypaev.link.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.tsypaev.link.domain.Link;
import ru.tsypaev.link.service.StatisticService;

import java.util.stream.Stream;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticController.class)
public class StatisticControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatisticService statisticService;

    private static final String YANDEX_URL = "https://www.yandex.ru";
    private static final String RAMBLER_URL = "https://www.rambler.ru";
    private static final String YANDEX_LINK = "d9c9bc4c";
    private static final String RAMBLER_LINK = "123456";

    @Test
    public void statisticControllerShouldGetLinkInfo() throws Exception {
        Link link = new Link(YANDEX_LINK, YANDEX_URL);
        Mockito.when(statisticService.getLinkInfo(YANDEX_LINK)).thenReturn(link);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/stats/" + YANDEX_LINK)
                .accept(MediaType.APPLICATION_JSON);

        String result = mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();
        String expected = "{\"link\":\"" + YANDEX_LINK + "\",\"original\":\"" + YANDEX_URL + "\",\"rank\":0,\"count\":0}";

        JSONAssert.assertEquals(expected, result, false);
    }

    @Test
    public void statisticControllerShouldReturnPageWithLinks() throws Exception {
        Link link1 = new Link(YANDEX_LINK, YANDEX_URL, 1, 5);
        Link link2 = new Link(RAMBLER_LINK, RAMBLER_URL, 3, 1);

        Stream<Link> links = Stream.of(link1, link2);


        Mockito.when(statisticService.getPageLinks(1, 2)).thenReturn(links);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/stats/?page=1&count=2")
                .accept(MediaType.APPLICATION_JSON);

        String result = mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();
        String expected = "[{\"link\":\"" + YANDEX_LINK + "\",\"original\":\"" + YANDEX_URL + "\",\"rank\":1,\"count\":5}," +
                "{\"link\":\"" + RAMBLER_LINK + "\",\"original\":\"" + RAMBLER_URL + "\",\"rank\":3,\"count\":1}]";

        JSONAssert.assertEquals(expected, result, false);
    }
}