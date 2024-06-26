package com.ems.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourcenotFoundException extends RuntimeException{

	public ResourcenotFoundException(String message) {
		super(message);
	}
}
