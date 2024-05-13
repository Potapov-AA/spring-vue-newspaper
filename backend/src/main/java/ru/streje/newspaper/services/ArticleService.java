package ru.streje.newspaper.services;

import java.util.List;

import ru.streje.newspaper.dtos.ArticleRequest;
import ru.streje.newspaper.dtos.ArticleResponse;
import ru.streje.newspaper.dtos.InfoMessageResponse;

public interface ArticleService {
	
	public List<ArticleResponse> getAllArticle();
	
	public ArticleResponse getArticle(int articleId);
	
	public InfoMessageResponse addNewArticle(ArticleRequest articleRequest);
	
	public InfoMessageResponse deleteArticle(int articleId);
	
	public InfoMessageResponse changeAtricle(ArticleRequest articleRequest, int articleId);
}
