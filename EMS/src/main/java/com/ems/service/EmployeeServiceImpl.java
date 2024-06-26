package com.ems.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ems.entity.Employee;
import com.ems.exception.EmailAlreadyExistException;
import com.ems.exception.ResourcenotFoundException;
import com.ems.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository repository;

	public EmployeeServiceImpl(EmployeeRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Employee> getEmployeeList() {
		return repository.findAll();
	}

	@Override
	public Employee createEmployee(Employee employee) throws EmailAlreadyExistException {
		if (repository.existsByEmail(employee.getEmail())) {
			throw new EmailAlreadyExistException("Email already exists: " + employee.getEmail());
		}
		return repository.save(employee);
	}

	@Override
	public Employee findEmployee(Integer empId) {
		Employee findEmp = repository.findById(empId)
				.orElseThrow(() -> new ResourcenotFoundException("Resource Not Found"));
		return findEmp;
	}

	@Override
	public Employee updateEmployee(Employee updateEmployee, Integer empId) {
		Employee existingEmployee = repository.findById(empId).orElse(null);
		if (existingEmployee != null) {
			existingEmployee.setFirstName(updateEmployee.getFirstName());
			existingEmployee.setLastName(updateEmployee.getLastName());
			existingEmployee.setDob(updateEmployee.getDob());
			existingEmployee.setAddress(updateEmployee.getAddress());
			existingEmployee.setEmail(updateEmployee.getEmail());
			existingEmployee.setGender(updateEmployee.getGender());
			existingEmployee.setPhoneNumber(updateEmployee.getPhoneNumber());
			existingEmployee.setJobTitle(updateEmployee.getJobTitle());
			existingEmployee.setSalary(updateEmployee.getSalary());
			return repository.save(existingEmployee);
		}
		throw new ResourcenotFoundException("Resource Not Found Exception");
	}

	@Override
	public String deleteEmployee(Integer empId) {
		Employee deleteEmployee = repository.findById(empId)
				.orElseThrow(() -> new ResourcenotFoundException("Resource Not Found Exception"));
		if (deleteEmployee != null) {
			repository.deleteById(empId);
			return "Delete Successfully";
		}
		throw new ResourcenotFoundException("Resource Not Found Exception");
	}

}
