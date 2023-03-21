package ru.job4j.url.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.url.model.Site;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SiteRepository {
    private final CrudRepository crudRepository;

    public boolean save(Site site) {
        crudRepository.run(session -> session.persist(site));
        return site.getId() != 0;
    }

    public Optional<Site> siteByLogin(String login) {
        return crudRepository.optional("from Site where login = :fLogin", Site.class, Map.of("fLogin", login));
    }
}
