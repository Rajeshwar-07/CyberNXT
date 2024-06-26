package com.ems.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer employeeId;

	@Column(name = "fname")
	@NotNull(message = "First name cannot be null")
	@Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
	private String firstName;

	@Column(name = "lname")
	@NotNull(message = "Last name cannot be null")
	@Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
	private String lastName;

	@Column(name = "dob")
	@Temporal(TemporalType.DATE)
	@Past(message = "Date of birth must be in the past")
	private Date dob;

	@Column(name = "gender")
	@NotNull(message = "Gender cannot be null")
	@Pattern(regexp = "MALE|FEMALE|OTHER", message = "Gender must be MALE, FEMALE, or OTHER")
	private String gender;

	@Column(name = "phone")
	@NotNull(message = "Phone number cannot be null")
	@Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
	private String phoneNumber;

	@Column(name = "email", unique = true)
	@NotNull(message = "Email cannot be null")
	@Email(message = "Email should be unique")
	private String email;

	@Column(name = "address")
	@NotNull(message = "Address cannot be null")
	@Size(min = 2, max = 100, message = "Address must be between 5 and 100 characters")
	private String address;

	@Column(name = "designation")
	@NotNull(message = "Job title cannot be null")
	@Size(min = 2, max = 50, message = "Job title must be between 2 and 50 characters")
	private String jobTitle;

	@Column(name = "salary")
	@NotNull(message = "Salary cannot be null")
	private Long salary;
}
