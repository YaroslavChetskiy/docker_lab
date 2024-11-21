package com.jcwc.dbpractice.domain.repository.jpa;

import com.jcwc.dbpractice.entity.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagazineRepository extends JpaRepository<Magazine, Integer> {
}
