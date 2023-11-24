package ru.streje.newspaper.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.dtos.ArticleRequest;
import ru.streje.newspaper.services.ArticleService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ArticleController {
	final private ArticleService articleService;

	@GetMapping("/")
	public ResponseEntity<?> getArticles() {
		return articleService.getAllArticle();
	}
	
	@PostMapping("/newarticle")
	public ResponseEntity<?> postNewArticle(@RequestBody ArticleRequest articleRequest) {
		return new ResponseEntity<>(articleService.addNewArticle(articleRequest), HttpStatus.OK);
	}
}
