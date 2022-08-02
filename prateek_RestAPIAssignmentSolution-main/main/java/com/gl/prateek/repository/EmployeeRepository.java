package com.gl.prateek.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gl.prateek.entity.EmployeeEntity;

@Repository
public interface EmployeeRepository  extends JpaRepository<EmployeeEntity,Integer> {
	List<EmployeeEntity> findByFirstNameContainsAllIgnoreCase(String firstName);
	List<EmployeeEntity> findAllByOrderByFirstNameAsc();
	List<EmployeeEntity> findAllByOrderByFirstNameDesc();

	
}
