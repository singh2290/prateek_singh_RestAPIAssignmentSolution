package com.gl.prateek.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gl.prateek.entity.EmployeeEntity;
import com.gl.prateek.entity.Role;
import com.gl.prateek.entity.User;
import com.gl.prateek.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	public EmployeeService employeeService;
	
	public EmployeeController(EmployeeService theEmployeeService) {
		employeeService=theEmployeeService;
	}

   // add mapping for "/employees"

	@GetMapping("/employees")
	public List<EmployeeEntity> allEmployee() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> cpa = auth.getAuthorities();
		System.out.println("inside all function: " + cpa);
		// get Employee from db
		List<EmployeeEntity> employee = employeeService.findAll();
		System.out.println("all employee record :" + employee);
		return employeeService.findAll();
	}
	
	@GetMapping("/employees/{employeeId}")
	public EmployeeEntity getEmployeeByID(@PathVariable int employeeId) {
		
		// get Employee from db
		EmployeeEntity employee = employeeService.findById(employeeId);
		if(employee==null) {
			throw new RuntimeException("Employee is not found "+employeeId);
		}
	
		return employee;
	}

	@PostMapping("/employees")
	public EmployeeEntity addEmployee(@RequestBody EmployeeEntity emp) {
		emp.setId(0);
		employeeService.save(emp);
		return emp;
	}
	
	@PutMapping("/employees/{employeeId}")
	public EmployeeEntity updateEmployee(@RequestBody EmployeeEntity emp,@PathVariable int employeeId) {
		// get Employee from db
				EmployeeEntity employee = employeeService.findById(employeeId);
				if(employee==null) {
					throw new RuntimeException("Employee is not found "+employeeId);
				}
		emp.setId(employeeId);
		employeeService.save(emp);
		return emp;
	}
	
	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployeeByID(@PathVariable int employeeId) {
		
		// get Employee from db
		EmployeeEntity employee = employeeService.findById(employeeId);
		if(employee==null) {
			throw new RuntimeException("Employee is not found "+employeeId);
		}
		employeeService.deleteById(employeeId);
		return "Deleted record "+employeeId +" successfully";
	}

	@GetMapping("/employees/search")
	public List<EmployeeEntity> searchByFirstName(@RequestParam(name="fname") String fname
			//@PathVariable String fname
			) {
		return employeeService.searchByfirstName(fname);
	}

	@GetMapping("/employees/sort")
	public List<EmployeeEntity> sortByFirstName(@RequestParam(name="order") String order){
		return employeeService.sortByFirstName(order);
	}

	@PostMapping("/user")
	public User saveUser(@RequestBody User user) {
		return employeeService.saveUser(user);
	}

	@PostMapping("/role")
	public Role saveRole(@RequestBody Role role) {
		return employeeService.saveRole(role);
	}
}
