package ru.streje.newspaper.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class MainController {
	@GetMapping("/")
	public ResponseEntity<?> getTasks(@RequestHeader("authorization") String token) {
		return new ResponseEntity<>(token, HttpStatus.OK);
	}
}
