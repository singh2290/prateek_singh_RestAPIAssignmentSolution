package com.prateeksingh.EmployeeManagementWebApp.service;

import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.prateeksingh.EmployeeManagementWebApp.model.EmpUserRole;
import com.prateeksingh.EmployeeManagementWebApp.model.Employee;
import com.prateeksingh.EmployeeManagementWebApp.model.Role;
import com.prateeksingh.EmployeeManagementWebApp.model.User;
import com.prateeksingh.EmployeeManagementWebApp.repository.RoleRepository;

@Service
public class RoleService implements IRoleService{

   @Autowired
   RoleRepository roleRepository;

   @Override
   public Role addRole(Role role) {
      return  roleRepository.save(role);
   }


   @Override
   public Page<Role> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
      Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
            Sort.by(sortField).descending();

      Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
      return this.roleRepository.findAll(pageable);
   }
}
