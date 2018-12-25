package ru.tsypaev.link.domain;


import javax.persistence.*;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Table(name = "LINKS")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "link", nullable = false)
    private String shortUrl;

    @Column(name = "original", nullable = false)
    private String fullUrl;

    @Column(name = "count", nullable = false)
    private int counter = new AtomicInteger(0).get();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Link() {
    }

    public Link(String shortUrl, String fullUrl) {
        this.shortUrl = shortUrl;
        this.fullUrl = fullUrl;
    }

}
