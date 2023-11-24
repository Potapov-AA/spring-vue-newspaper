package ru.streje.newspaper.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ResponseEntity<?> postArticle(@RequestBody ArticleRequest articleRequest) {
		return articleService.addNewArticle(articleRequest);
	}
	
	@DeleteMapping("/deletearticle/{id}")
	public ResponseEntity<?> deleteArticle(@PathVariable("id") Integer id) {
		return articleService.deleteArticle(id);
	}
}
