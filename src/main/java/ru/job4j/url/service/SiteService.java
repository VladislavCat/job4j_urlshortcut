package ru.job4j.url.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.url.model.Site;
import ru.job4j.url.repository.SiteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Service
@AllArgsConstructor
public class SiteService {
    private final SiteRepository siteRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean save(Site site) {
        site.setLogin(site.getName() + "_user");
        return siteRepository.save(site);
    }

    public String generatedPasswordOnSiteName(String name) {
        name = name.replace('.', 'A');
        List<Integer> list = new ArrayList<>(name.length());
        for (int i = 0; i < name.length(); i++) {
            list.add((int) (Math.random() * name.length()));
        }
        StringBuilder password = new StringBuilder();
        for (int i : list) {
            if (i % 3 == 0) {
                password.append(name.toUpperCase(Locale.ROOT).charAt(i));
            } else {
                password.append(name.charAt(i));
            }
            password.append(list.get(i));
        }
        return password.substring(0, 8);
    }
}
