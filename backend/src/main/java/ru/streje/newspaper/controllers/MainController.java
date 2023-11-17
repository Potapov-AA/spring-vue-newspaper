package ru.streje.newspaper.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.services.ArticleService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class MainController {
	final private ArticleService articleService;
	
	@GetMapping("/")
	public ResponseEntity<?> getArticle(@RequestHeader("authorization") String token) {
		return new ResponseEntity<>(articleService.getAllArticle(), HttpStatus.OK);
	}
}
