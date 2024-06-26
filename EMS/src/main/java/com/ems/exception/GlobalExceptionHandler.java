package com.ems.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
			WebRequest request) {
		List<Map<String, String>> errors = ex.getConstraintViolations().stream().map(e -> {
			Map<String, String> error = new HashMap<>();
			error.put(e.getPropertyPath().toString(), e.getMessageTemplate());
			return error;
		}).collect(Collectors.toList());

		Map<String, Object> response = new HashMap<>();
		response.put("errors", errors);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourcenotFoundException.class)
	public final ResponseEntity<Object> handleResourcenotFoundException(Exception ex) {
		Map<String, String> map = new HashMap<>();
		map.put("error", ex.getMessage());
		return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EmailAlreadyExistException.class)
	public final ResponseEntity<Object> handleEmailAlreadyExistingException(Exception ex) {
		Map<String, String> map = new HashMap<>();
		map.put("error", ex.getMessage());
		return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
	}
}
