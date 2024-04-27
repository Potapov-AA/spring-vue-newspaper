package ru.streje.newspaper.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.dtos.ArticleRequest;
import ru.streje.newspaper.dtos.ArticleResponse;
import ru.streje.newspaper.dtos.InfoMessageResponse;
import ru.streje.newspaper.services.ArticleService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ArticleController {
	final private ArticleService articleService;

	
	@GetMapping("/articles")
	public List<ArticleResponse> getArticles() {
		
		return articleService.getAllArticle();
	}

	
	@GetMapping("/article/{id}")
	public ArticleResponse getArticle(@PathVariable("id") Integer id) {
		
		return articleService.getArticle(id);
	}

	
	@PostMapping("/newarticle")
	public InfoMessageResponse postArticle(@Valid @RequestBody ArticleRequest articleRequest) {
		
		return articleService.addNewArticle(articleRequest);
	}

	
	@DeleteMapping("/deletearticle/{id}")
	public InfoMessageResponse deleteArticle(@PathVariable("id") Integer id) {
		
		return articleService.deleteArticle(id);
	}

	
	@PutMapping("/changearticle/{id}")
	public InfoMessageResponse putArticle(
			@Valid @RequestBody ArticleRequest articleRequest,
			@PathVariable("id") Integer id) {
		
		return articleService.changeAtricle(articleRequest, id);
	}
}
