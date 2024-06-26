package com.ems.service;

import java.util.List;


import com.ems.entity.Employee;
import com.ems.exception.EmailAlreadyExistException;

public interface EmployeeService {

	public List<Employee> getEmployeeList();
	
	public Employee createEmployee(Employee employee) throws EmailAlreadyExistException;
	
	public Employee findEmployee(Integer empId);
	
	public Employee updateEmployee(Employee updateEmployee, Integer empId);
	
	public String deleteEmployee(Integer empId);
}
