package ru.streje.newspaper.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.streje.newspaper.messages.ErrorMessage;

@ControllerAdvice
public class DefaultAdvice {
	
	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> validateExeptions(MethodArgumentNotValidException e) {
		
		String message = e.getBindingResult().getFieldError().getDefaultMessage();
		
		return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), message), HttpStatus.BAD_REQUEST);
	}
}
