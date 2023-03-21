package ru.job4j.url.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.url.model.Site;
import ru.job4j.url.repository.SiteRepository;

import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final SiteRepository sites;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Site> site = sites.siteByLogin(username);
        if (site.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new User(site.get().getLogin(), site.get().getPassword(), emptyList());
    }
}
