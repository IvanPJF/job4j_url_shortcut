package ru.job4j.shortcut.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.shortcut.RunApplication;
import ru.job4j.shortcut.model.Link;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.service.LinkService;
import ru.job4j.shortcut.service.SiteService;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.job4j.shortcut.controller.ConvertController.*;

@SpringBootTest(classes = RunApplication.class)
@AutoConfigureMockMvc
public class ConvertControllerTest {

    @MockBean
    private LinkService linkService;

    @MockBean
    private SiteService siteService;

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser()
    @Test
    public void whenConvertSuccessThenResponseStatusOk() throws Exception {
        Link link = Link.of("https://job4j.ru/courses/java_with_zero_to_job.html");
        String reqJson = new ObjectMapper().writeValueAsString(link);
        String code = "abc";
        Link linkWithCode = Link.of(link.getUrl());
        linkWithCode.setCode(code);
        String respJson = new ObjectMapper().writeValueAsString(Code.of(code));
        when(siteService.findByLogin("mock_login")).thenReturn(Site.of("mock_site"));
        when(linkService.saveAndGenerateCode(link)).thenReturn(Optional.of(linkWithCode));
        mockMvc.perform(post("/convert").contentType(MediaType.APPLICATION_JSON)
                .content(reqJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(respJson));
    }

    @WithMockUser()
    @Test
    public void whenConvertNotSuccessThenResponseStatusBadRequest() throws Exception {
        Link link = Link.of("https://same-site.ru/same_page.html");
        String reqJson = new ObjectMapper().writeValueAsString(link);
        String respJson = new ObjectMapper().writeValueAsString(Code.bad());
        when(siteService.findByLogin("mock_login")).thenReturn(Site.of("mock_site"));
        when(linkService.saveAndGenerateCode(link)).thenReturn(Optional.empty());
        mockMvc.perform(post("/convert").contentType(MediaType.APPLICATION_JSON)
                .content(reqJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(respJson));
    }

}