package ru.tsypaev.link.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tsypaev.link.domain.Link;

@Repository
public interface LinkRepository extends CrudRepository<Link, Long> {

    Link findByShortUrl(String shortUrl);

    Link findByFullUrl(String fullUrl);

}


