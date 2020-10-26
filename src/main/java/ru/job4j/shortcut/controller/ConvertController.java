package ru.job4j.shortcut.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import ru.job4j.shortcut.model.Link;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.service.LinkService;
import ru.job4j.shortcut.service.SiteService;

@RestController
@RequestMapping("/convert")
public class ConvertController {

    private final LinkService linkService;
    private final SiteService siteService;

    public ConvertController(LinkService linkService, SiteService siteService) {
        this.linkService = linkService;
        this.siteService = siteService;
    }

    @PostMapping
    public ResponseEntity<Code> convert(@RequestBody Link link) {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Site site = siteService.findByLogin(authUser.getUsername());
        link.setSite(site);
        return linkService.saveAndGenerateCode(link)
                .map(value -> new ResponseEntity<>(Code.of(value.getCode()), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(Code.bad(), HttpStatus.BAD_REQUEST));
    }

    public static class Code {
        private String code;

        public static Code of(String code) {
            Code rsl = new Code();
            rsl.setCode(code);
            return rsl;
        }

        public static Code bad() {
            return of("");
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
