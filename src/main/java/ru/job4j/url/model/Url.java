package ru.job4j.url.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ursl")
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String url;
    @Column(name = "url_key")
    private String key;
    @Column(name = "redirect_count")
    private int count;
}
