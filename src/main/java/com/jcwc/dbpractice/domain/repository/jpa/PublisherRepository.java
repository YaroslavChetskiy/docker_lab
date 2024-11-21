package com.jcwc.dbpractice.domain.repository.jpa;

import com.jcwc.dbpractice.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
}
