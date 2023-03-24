package ru.job4j.url.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.url.model.Url;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataUrlRepository extends CrudRepository<Url, Integer> {
    List<Url> findAll();
    Optional<Url> findByKey(String key);
    @Transactional
    @Modifying
    @Query("update Url u set u.count = u.count + 1 where id = :id")
    void incrementRequestCount(@Param("id") int id);
}
