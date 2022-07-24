package com.prateeksingh.EmployeeManagementWebApp.dto;

import java.util.List;

import com.prateeksingh.EmployeeManagementWebApp.model.Employee;
import com.prateeksingh.EmployeeManagementWebApp.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResultDto {
   List<Employee> employeeList;
}
