package com.prateeksingh.EmployeeManagementWebApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prateeksingh.EmployeeManagementWebApp.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
   Role findByName(@Param("name") String name);
   List<Role> findByIdIn(@Param("role_ids")List<Long> roleIds);

}
