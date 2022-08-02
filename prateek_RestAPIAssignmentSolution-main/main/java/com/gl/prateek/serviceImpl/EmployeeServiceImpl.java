package com.gl.prateek.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gl.prateek.entity.EmployeeEntity;
import com.gl.prateek.entity.Role;
import com.gl.prateek.entity.User;
import com.gl.prateek.repository.EmployeeRepository;
import com.gl.prateek.repository.UserRepository;
import com.gl.prateek.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public List<EmployeeEntity> findAll() {
		List<EmployeeEntity> emp = employeeRepository.findAll();
		return emp;
	}

	@Override
	public EmployeeEntity findById(int theId) {
		Optional<EmployeeEntity> res = employeeRepository.findById(theId);
		EmployeeEntity em = null;
		if (res.isPresent()) {
			em = res.get();
		} else {
			throw new RuntimeException("Employee with Id " + theId + " not found");
		}
		return em;

	}

	@Override
	public void save(EmployeeEntity employee) {
		employeeRepository.save(employee);

	}

	@Override
	public void deleteById(int theId) {
		employeeRepository.deleteById(theId);

	}

	@Override
	public List<EmployeeEntity> sortByFirstName(String sortType) {
		if (sortType.equalsIgnoreCase("desc")) {
			return employeeRepository.findAllByOrderByFirstNameDesc();
		} else {
			return employeeRepository.findAllByOrderByFirstNameAsc();
		}
	}

	@Override
	public List<EmployeeEntity> searchByfirstName(String firstName) {
		return employeeRepository.findByFirstNameContainsAllIgnoreCase(firstName);
	}

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		System.out.print(user.toString());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		System.out.print("after: "+ user.toString());
		return userRepository.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		// TODO Auto-generated method stub
		return roleRepository.save(role);
	}

}
