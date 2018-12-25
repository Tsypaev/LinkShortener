package ru.tsypaev.link.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tsypaev.link.domain.Link;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface LinkRepository extends CrudRepository<Link, Long> {

    Link findByLink(String link);

    Link findByOriginal(String original);

    List<Link> findAll(String name, Pageable page);

}


