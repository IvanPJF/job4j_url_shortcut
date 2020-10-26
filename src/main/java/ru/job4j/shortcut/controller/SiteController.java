package ru.job4j.shortcut.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.service.SiteService;

@RestController
@RequestMapping("/registration")
public class SiteController {

    private static final int PASSWORD_LENGTH = 8;

    private final SiteService siteService;
    private final PasswordEncoder encoder;

    public SiteController(SiteService siteService, PasswordEncoder encoder) {
        this.siteService = siteService;
        this.encoder = encoder;
    }

    @PostMapping
    public ResponseEntity<Response> registration(@RequestBody Site site) {
        String password = RandomStringUtils.random(PASSWORD_LENGTH, true, true);
        site.setLogin(site.getSite());
        site.setPassword(encoder.encode(password));
        return siteService.save(site)
                ? new ResponseEntity<>(Response.of(true, site.getLogin(), password), HttpStatus.OK)
                : new ResponseEntity<>(Response.bad(), HttpStatus.BAD_REQUEST);
    }

    private static class Response {
        private boolean registration;
        private String login;
        private String password;

        public static Response of(boolean registration, String login, String password) {
            Response response = new Response();
            response.registration = registration;
            response.login = login;
            response.password = password;
            return response;
        }

        public static Response bad() {
            return of(false, "", "");
        }

        public boolean isRegistration() {
            return registration;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }
    }
}
