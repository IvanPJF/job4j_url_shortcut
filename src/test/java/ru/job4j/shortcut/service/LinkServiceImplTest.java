package ru.job4j.shortcut.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.job4j.shortcut.RunApplication;
import ru.job4j.shortcut.model.Link;
import ru.job4j.shortcut.repository.LinkRepository;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = RunApplication.class)
public class LinkServiceImplTest {

    @MockBean
    private LinkRepository linkRepository;

    @Autowired
    private LinkService linkService;

    @Test
    public void whenSaveAndGenerateCodeForSavedLinkThenLinkIsPresentWithSavedCode() {
        Link linkInput = Link.of("https://job4j.ru");
        Link linkReturn = Link.of(linkInput.getUrl());
        linkReturn.setCode("abc");
        when(linkRepository.findByUrl(linkInput.getUrl())).thenReturn(Optional.of(linkReturn));
        Optional<Link> result = linkService.saveAndGenerateCode(linkInput);
        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getCode(), is(linkReturn.getCode()));
    }

    @Test
    public void whenSaveAndGenerateCodeForNewLinkThenLinkIsPresentWithCodeNotNull() {
        Link linkInput = Link.of("https://some-site.ru");
        when(linkRepository.findByUrl(linkInput.getUrl())).thenReturn(Optional.empty());
        when(linkRepository.save(linkInput)).thenReturn(linkInput);
        Optional<Link> result = linkService.saveAndGenerateCode(linkInput);
        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getCode(), not(blankOrNullString()));
    }

    @Test
    public void whenFindByCodeThenLinkIsPresent() {
        String code = "abc";
        Link linkReturn = Link.of("https://job4j.ru");
        linkReturn.setCode(code);
        when(linkRepository.findByCode(code)).thenReturn(Optional.of(linkReturn));
        Optional<Link> result = linkService.findByCode(code);
        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getCode(), is(linkReturn.getCode()));
        assertThat(result.get().getUrl(), is(linkReturn.getUrl()));
    }
}