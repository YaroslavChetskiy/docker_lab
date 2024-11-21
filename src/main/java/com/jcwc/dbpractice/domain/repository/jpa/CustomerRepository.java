package com.jcwc.dbpractice.domain.repository.jpa;

import com.jcwc.dbpractice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
