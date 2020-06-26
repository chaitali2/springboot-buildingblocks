package com.stacksimplify.restservices.springbootbuildingblocks.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GloablRestControllerAdviceExceptionHandler {
	@ExceptionHandler(UserNameNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public CustomErrorDetails usernameNotFound(UserNameNotFoundException ex) {
		return new CustomErrorDetails(new Date(), "From @RestcontrollerAdvice Not Found", ex.getMessage());
	}
}
