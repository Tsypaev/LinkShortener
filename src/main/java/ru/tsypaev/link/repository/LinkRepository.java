package ru.tsypaev.link.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.tsypaev.link.domain.Link;

import java.util.List;

@Repository
public interface LinkRepository extends CrudRepository<Link, Long>, PagingAndSortingRepository<Link, Long> {

    Link findByLink(String link);

    Link findByOriginal(String original);

    List<Link> findAllByOrderByCountDesc();
}


