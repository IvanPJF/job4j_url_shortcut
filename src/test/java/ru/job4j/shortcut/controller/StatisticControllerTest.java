package ru.job4j.shortcut.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.shortcut.RunApplication;
import ru.job4j.shortcut.model.Link;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.service.SiteService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.job4j.shortcut.controller.StatisticController.*;

@SpringBootTest(classes = RunApplication.class)
@AutoConfigureMockMvc
public class StatisticControllerTest {

    private final static String USERNAME = "username";

    @MockBean
    private SiteService siteService;

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(username = USERNAME)
    @Test
    public void whenStatisticThenStatusOk() throws Exception {
        Link linkFirst = Link.of("https://job4j.ru/courses/java_courses.html", 10);
        Link linkSecond = Link.of("https://job4j.ru/blogs.html", 20);
        String respJson = new ObjectMapper().writeValueAsString(List.of(
                Response.of(linkFirst.getUrl(), linkFirst.getTotal()),
                Response.of(linkSecond.getUrl(), linkSecond.getTotal()))
        );
        Site site = Site.of("https://job4j.ru");
        site.setLinks(List.of(linkFirst, linkSecond));
        when(siteService.findWithAllFieldsByLogin(USERNAME)).thenReturn(site);
        mockMvc.perform(get("/statistic"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(respJson));
    }
}