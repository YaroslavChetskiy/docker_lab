package com.jcwc.dbpractice.domain.repository.jpa;

import com.jcwc.dbpractice.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {
}
