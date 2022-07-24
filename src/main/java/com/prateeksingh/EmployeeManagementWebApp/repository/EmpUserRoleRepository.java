package com.prateeksingh.EmployeeManagementWebApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prateeksingh.EmployeeManagementWebApp.model.EmpUserRole;

@Repository
public interface EmpUserRoleRepository extends CrudRepository<EmpUserRole, Long> {
   List<EmpUserRole> findAllByEmpUserId(@Param("emp_user_id") long empUserId);
}
