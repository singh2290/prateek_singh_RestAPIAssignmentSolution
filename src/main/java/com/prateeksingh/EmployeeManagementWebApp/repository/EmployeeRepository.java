package com.prateeksingh.EmployeeManagementWebApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prateeksingh.EmployeeManagementWebApp.model.Employee;
import com.prateeksingh.EmployeeManagementWebApp.model.User;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
   List<Employee> findAllByFirstName(@Param("firstName") String firstName);
}
