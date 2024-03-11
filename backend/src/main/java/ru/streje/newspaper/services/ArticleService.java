package ru.streje.newspaper.services;

import org.springframework.http.ResponseEntity;

import ru.streje.newspaper.dtos.ArticleRequest;
import ru.streje.newspaper.models.Article;

public interface ArticleService {
	
	public ResponseEntity<?> getAllArticle();
	
	public ResponseEntity<?> getArticleResponse(int articleId);
	
	public ResponseEntity<?> addNewArticle(ArticleRequest articleRequest);
	
	public ResponseEntity<?> deleteArticle(int articleId);
	
	public ResponseEntity<?> changeAtricle(ArticleRequest articleRequest, int articleId);
	
	public Article getArticle(int articleId);
	
	public void saveArticle(Article article);
}
