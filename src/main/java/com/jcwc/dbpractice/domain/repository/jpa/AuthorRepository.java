package com.jcwc.dbpractice.domain.repository.jpa;

import com.jcwc.dbpractice.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
