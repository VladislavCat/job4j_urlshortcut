package ru.job4j.url.controller;

import lombok.AllArgsConstructor;
import org.apache.tomcat.jni.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.url.model.Site;
import ru.job4j.url.model.Url;
import ru.job4j.url.service.SiteService;
import ru.job4j.url.service.UrlService;

import javax.validation.Valid;
import java.util.*;

@RestController
@AllArgsConstructor
public class Controller {
    private final SiteService service;
    private final UrlService urlService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/registration")
    public ResponseEntity<Map<String, String>> registration(@Valid @RequestBody Map<String, String> map) {
        Site site = new Site();
        site.setName(map.get("site"));
        String password = service.generatedPasswordOnSiteName(site.getName());
        site.setPassword(bCryptPasswordEncoder.encode(password));
        return ResponseEntity.of(Optional.of(new HashMap<>() {{
            put("registration", String.valueOf(service.save(site)));
            put("login", site.getLogin());
            put("password", password);
        }}));
    }

    @PostMapping("/convert")
    public ResponseEntity<String> convert(@RequestBody Map<String, String> map) {
        Url url = new Url();
        url.setUrl(map.get("url"));
        return urlService.save(url) ? ResponseEntity.of(Optional.of(url.getKey()))
                : ResponseEntity.badRequest().body("This URL already registered");
    }

    @GetMapping("/redirect/{key}")
    public ResponseEntity<?> redirect(@PathVariable("key") String key) {
        Optional<Url> url = urlService.findByKey(key);
        if (url.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("HTTP CODE - 302 REDIRECT URL")
                .body(url.get().getUrl());
    }

    @GetMapping("/statistic")
    public ResponseEntity<Map<String, Integer>> statistic() {
        return ResponseEntity.of(Optional.of(urlService.findAllStatistic()));
    }


}
