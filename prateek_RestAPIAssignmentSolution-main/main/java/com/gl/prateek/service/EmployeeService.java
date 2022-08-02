package com.gl.prateek.service;

import java.util.List;

import com.gl.prateek.entity.EmployeeEntity;
import com.gl.prateek.entity.Role;
import com.gl.prateek.entity.User;


public interface EmployeeService {

	List<EmployeeEntity> findAll();
	
	public EmployeeEntity findById(int theId);

	public void save(EmployeeEntity employee);

	public void deleteById(int theId);
	
	public List<EmployeeEntity> sortByFirstName(String sortType);


	public List<EmployeeEntity> searchByfirstName(String firstName);

	public User saveUser(User user);

	public Role saveRole(Role role);

}
