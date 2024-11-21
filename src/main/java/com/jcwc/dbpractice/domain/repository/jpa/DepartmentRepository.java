package com.jcwc.dbpractice.domain.repository.jpa;

import com.jcwc.dbpractice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
