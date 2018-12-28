package ru.tsypaev.link.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.tsypaev.link.service.LinkService;


import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GenerateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LinkService linkService;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(new GenerateController(linkService)).build();
    }

    /*@Test
    public void shouldReturnShortUrl() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/generate")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes("https://www.yandex.ru"))
                .characterEncoding("UTF-8"))
//                .andExpect(status().isOk())
                .andExpect(content().string("d"));
//                .andExpect(content().string(equalTo("")));
    }*/

}