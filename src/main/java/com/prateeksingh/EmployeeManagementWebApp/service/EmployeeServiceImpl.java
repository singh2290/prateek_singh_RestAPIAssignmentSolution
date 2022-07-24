package com.prateeksingh.EmployeeManagementWebApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.prateeksingh.EmployeeManagementWebApp.model.Employee;
import com.prateeksingh.EmployeeManagementWebApp.model.User;
import com.prateeksingh.EmployeeManagementWebApp.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

   @Autowired
   private EmployeeRepository employeeRepository;

   @Override
   public List<Employee> getAllEmployees() {
      Iterable<Employee> employessIter =  employeeRepository.findAll();
      List<Employee> result =
            StreamSupport.stream(employessIter.spliterator(), false)
                  .collect(Collectors.toList());
      return result;
   }

   @Override
   public void saveEmployee(Employee employee) {
      this.employeeRepository.save(employee);
   }

   @Override
   public Employee getEmployeeById(long id) {
      Optional<Employee> optional = employeeRepository.findById(id);
      Employee employee = null;
      if (optional.isPresent()) {
         employee = optional.get();
      } else {
         throw new RuntimeException(" Employee not found for id :: " + id);
      }
      return employee;
   }

   @Override
   public void deleteEmployeeById(long id) {
      this.employeeRepository.deleteById(id);
   }

   @Override
   public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
      Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
            Sort.by(sortField).descending();

      Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
      return this.employeeRepository.findAll(pageable);
   }

   @Override
   public List<Employee> searchEmployee(Long id, String keyWord) {
      List<Employee> employeeList = new ArrayList<>();
      if (id != null) {
         Optional<Employee> employeeOptional =  this.employeeRepository.findById(id);
         Employee employee = employeeOptional.orElseGet(Employee::new);
         employeeList.add(employee);
      }
      if (!ObjectUtils.isEmpty(keyWord)) {
         List<Employee> list = this.employeeRepository.findAllByFirstName(keyWord);
         if (!CollectionUtils.isEmpty(list)) {
            employeeList.addAll(list);
         }
      }
      return employeeList;
   }
}
