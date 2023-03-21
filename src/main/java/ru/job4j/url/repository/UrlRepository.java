package ru.job4j.url.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.url.model.Url;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@AllArgsConstructor
public class UrlRepository {
    private final CrudRepository crudRepository;

    public boolean save(Url url) {
        crudRepository.run(session -> session.save(url));
        return url.getId() != 0;
    }

    public List<Url> findAll() {
        return crudRepository.query("from Url", Url.class);
    }

    public Optional<Url> findByKey(String key) {
        Optional<Url> url = crudRepository.optional("from Url where url_key = :fKey", Url.class, Map.of("fKey", key));
        url.ifPresent(this::incrementRedirectCount);
        return url;
    }

    public void incrementRedirectCount(Url url) {
        url.getCount().incrementAndGet();
        crudRepository.run(session -> session.update(url));
    }
}
