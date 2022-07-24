package com.prateeksingh.EmployeeManagementWebApp.service;

import org.springframework.data.domain.Page;

import com.prateeksingh.EmployeeManagementWebApp.model.Employee;
import com.prateeksingh.EmployeeManagementWebApp.model.Role;

public interface IRoleService {
   Role addRole(Role role);
   Page<Role> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
