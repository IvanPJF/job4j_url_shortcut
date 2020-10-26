package ru.job4j.shortcut.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.service.SiteService;

import java.util.List;

import static java.util.stream.Collectors.*;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    private final SiteService siteService;

    public StatisticController(SiteService siteService) {
        this.siteService = siteService;
    }

    @GetMapping
    public ResponseEntity<List<Response>> statistic() {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Site site = siteService.findWithAllFieldsByLogin(authUser.getUsername());
        return new ResponseEntity<>(
                site.getLinks().stream().map(link -> Response.of(link.getUrl(), link.getTotal()))
                        .collect(toList()),
                HttpStatus.OK
        );
    }

    public static class Response {

        private String url;
        private int total;

        public static Response of(String url, int total) {
            Response response = new Response();
            response.setUrl(url);
            response.setTotal(total);
            return response;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}
