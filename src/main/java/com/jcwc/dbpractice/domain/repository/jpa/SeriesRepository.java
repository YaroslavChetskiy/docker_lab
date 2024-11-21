package com.jcwc.dbpractice.domain.repository.jpa;

import com.jcwc.dbpractice.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Integer> {
}
