package ru.job4j.shortcut.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.shortcut.RunApplication;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.service.SiteService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = RunApplication.class)
@AutoConfigureMockMvc
public class SiteControllerTest {

    @MockBean
    private SiteService siteService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenRegistrationSiteSuccessThenResponseStatusOk() throws Exception {
        Site site = Site.of("job4j.ru");
        String reqJson = new ObjectMapper().writeValueAsString(site);
        when(siteService.save(site)).thenReturn(true);
        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON)
                .content(reqJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenRegistrationSiteNotSuccessThenResponseStatusBadRequest() throws Exception {
        Site site = Site.of("javarush.ru");
        String reqJson = new ObjectMapper().writeValueAsString(site);
        when(siteService.save(site)).thenReturn(false);
        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON)
                .content(reqJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}