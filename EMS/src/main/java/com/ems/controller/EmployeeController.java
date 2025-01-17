package com.ems.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.ems.entity.Employee;
import com.ems.exception.EmailAlreadyExistException;
import com.ems.service.EmployeeService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping(produces = "text/html")
	public String listEmployeesView(Model model) {
		model.addAttribute("employees", employeeService.getEmployeeList());
		return "employees";
	}

	@GetMapping("/new")
	public String createEmployeeForm(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "create_employee";
	}

	@PostMapping
	public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, Model model)
			throws EmailAlreadyExistException {
		if (result.hasErrors()) {
			model.addAttribute("employee", employee);
			return "create_employee";
		}
		employeeService.createEmployee(employee);
		return "redirect:/employees";
	}

	@GetMapping(value = "/{id}", produces = "text/html")
	public String getEmployeeByIdForm(@PathVariable Integer id, Model model) {
		Employee employee = employeeService.findEmployee(id);
		model.addAttribute("employee", employee);
		return "employee_detail";
	}

	@GetMapping("/{id}/edit")
	public String editEmployeeForm(@PathVariable Integer id, Model model) {
		Employee employee = employeeService.findEmployee(id);
		model.addAttribute("employee", employee);
		return "edit_employee";
	}

	@PostMapping("/{id}/update")
	public String updateEmployee(@PathVariable Integer id, @Valid @ModelAttribute("employee") Employee employee,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("employee", employee);
			return "edit_employee";
		}
		employeeService.updateEmployee(employee, id);
		return "redirect:/employees";
	}

	@PostMapping("/{id}/delete")
	public String deleteEmployee(@PathVariable Integer id) {
		employeeService.deleteEmployee(id);
		return "redirect:/employees";
	}
	

	// (handle JSON input via Postman)

	@PostMapping(consumes = "application/json")
	@ResponseBody
	public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) throws EmailAlreadyExistException {
		Employee saveEmployee = employeeService.createEmployee(employee);
		return new ResponseEntity<Employee>(saveEmployee, HttpStatus.CREATED);
	}

	@GetMapping(produces = "application/json")
	@ResponseBody
	public ResponseEntity<List<Employee>> listEmployeesJson() {
		List<Employee> employees = employeeService.getEmployeeList();
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	@ResponseBody
	public Employee getEmployeeByIdJson(@PathVariable Integer id) {
		Employee employee = employeeService.findEmployee(id);
		return employee;
	}

	@PutMapping(value = "/{id}/update", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<Employee> updateEmployeeJson(@PathVariable Integer id, @RequestBody Employee employee) {
		Employee updatedEmployee = employeeService.updateEmployee(employee, id);
		return new ResponseEntity<Employee>(updatedEmployee, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}/delete")
	@ResponseBody
	public ResponseEntity<String> deleteEmployeeJson(@PathVariable Integer id) {
		String deleteEmployee = employeeService.deleteEmployee(id);
		return ResponseEntity.ok(deleteEmployee);
	}
}
