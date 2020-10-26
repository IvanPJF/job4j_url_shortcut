package ru.job4j.shortcut.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.shortcut.model.Link;
import ru.job4j.shortcut.service.LinkService;

import java.util.Optional;

@RestController
@RequestMapping("/redirect")
public class RedirectController {

    private final LinkService linkService;

    public RedirectController(LinkService linkService) {
        this.linkService = linkService;
    }

    @Transactional
    @GetMapping("{code}")
    public ResponseEntity<Void> redirect(@PathVariable("code") String code) {
        Optional<Link> foundLink = linkService.findByCode(code);
        if (foundLink.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Link link = foundLink.get();
        link.setTotal(link.getTotal() + 1);
        HttpStatus statusResponse = HttpStatus.FOUND;
        return new ResponseEntity<>(buildHeaders(link, statusResponse), statusResponse);
    }


    private HttpHeaders buildHeaders(Link link, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("HTTP CODE", String.valueOf(status.value()));
        headers.add("REDIRECT", link.getUrl());
        return headers;
    }
}
