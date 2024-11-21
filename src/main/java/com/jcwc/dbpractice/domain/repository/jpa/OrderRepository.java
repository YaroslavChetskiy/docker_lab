package com.jcwc.dbpractice.domain.repository.jpa;

import com.jcwc.dbpractice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
