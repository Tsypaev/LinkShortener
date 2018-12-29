package ru.tsypaev.link.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GenerateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnShortLink() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/generate")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"original\":\"https://www.yandex.ru\"}")
                .characterEncoding("UTF-8");

        String result = this.mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();
        String expected = "{\"link\":\"/l/d9c9bc4c\"}";

        JSONAssert.assertEquals(expected, result, false);
    }
}