package ru.job4j.shortcut.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.shortcut.RunApplication;
import ru.job4j.shortcut.model.Link;
import ru.job4j.shortcut.service.LinkService;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = RunApplication.class)
@AutoConfigureMockMvc
public class RedirectControllerTest {

    @MockBean
    private LinkService linkService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenRedirectSuccessThenStatusFound() throws Exception {
        String resultLink = "https://job4j.ru/courses/java_with_zero_to_job.html";
        String code = "abc";
        when(linkService.findByCode(code)).thenReturn(Optional.of(Link.of(resultLink)));
        mockMvc.perform(get("/redirect/{code}", code))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(header().string("HTTP CODE", String.valueOf(HttpStatus.FOUND.value())))
                .andExpect(header().string("REDIRECT", resultLink));
    }

    @Test
    public void whenRedirectNotSuccessThenStatusBadRequest() throws Exception {
        String badCode = "bad_code";
        when(linkService.findByCode(badCode)).thenReturn(Optional.empty());
        mockMvc.perform(get("/redirect/{code}", badCode))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}