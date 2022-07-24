package com.prateeksingh.EmployeeManagementWebApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prateeksingh.EmployeeManagementWebApp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   User findByEmail(String email);
   List<User> findAllByFirstName(@Param("name") String name);
}
