package ru.tsypaev.link.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private String link;

    @Column(name = "original", nullable = false)
    private String original;

    @Column(name = "count", nullable = false)
    private int count = new AtomicInteger(0).get();

    @Column(name = "rank", nullable = false)
    private int rank;

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Link() {
    }

    public Link(String link, String original) {
        this.link = link;
        this.original = original;
    }

}
