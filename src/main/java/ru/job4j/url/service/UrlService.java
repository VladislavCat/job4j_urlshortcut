package ru.job4j.url.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import ru.job4j.url.model.Url;
import ru.job4j.url.repository.UrlRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.CRC32;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository repository;

    public boolean save(Url url) {
        url.setKey(stringHash(url.getUrl()));
        url.setCount(new AtomicInteger(0));
        return repository.save(url);
    }

    public Optional<Url> findByKey(String key) {
        return repository.findByKey(key);
    }

    private String stringHash(String url) {
        byte[] b = DigestUtils.sha1(url);
        StringBuilder result = new StringBuilder();
        for (byte value : b) {
            result.append(Integer.toString((value & 0xff) + 0x100, 16).substring(1));
        }
        return result.substring(0, 10);
    }

    public Map<String, Integer> findAllStatistic() {
        Map<String, Integer> rsl = new HashMap<>();
        List<Url> urls = repository.findAll();
        for (Url url : urls) {
            rsl.put(url.getKey(), url.getCount().get());
        }
        return rsl;
    }
}
