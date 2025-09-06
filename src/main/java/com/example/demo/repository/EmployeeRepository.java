package com.example.demo.repository;

import com.example.demo.entity.Employee;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
  // Find employee with supervisor = id
  List<Employee> findBySupervisor(Integer supervisor);

  @Query("SELECT e FROM Employee e " +
      "WHERE LOWER(e.lastname) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
      "   OR LOWER(e.firstname) LIKE LOWER(CONCAT('%', :keyword, '%'))")
  List<Employee> searchByName(@Param("keyword") String keyword);
}
