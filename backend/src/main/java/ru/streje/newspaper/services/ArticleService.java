package ru.streje.newspaper.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.dtos.ArticleRequest;
import ru.streje.newspaper.dtos.ArticlesResponse;
import ru.streje.newspaper.messages.ErrorMessage;
import ru.streje.newspaper.messages.SuccesMessage;
import ru.streje.newspaper.models.Article;
import ru.streje.newspaper.models.Theme;
import ru.streje.newspaper.repositories.ArticleRepository;

@Service
@RequiredArgsConstructor
public class ArticleService {
	private final ArticleRepository articleRepository;
	
	/**
	 * Метод возвращает все статьи
	 * @return список всех статей (ArticleResponse)
	 */
	public ResponseEntity<?> getAllArticle() {
		List<ArticlesResponse> articles = new ArrayList<>();
		
		Iterable<Article> iArticles = articleRepository.findAll();
		
		for (Article article : iArticles) {
			ArticlesResponse articleRes = new ArticlesResponse();
			articleRes.setId(article.getId());
			articleRes.setTitle(article.getTitle());
			articleRes.setText(article.getText());
			articleRes.setImage(null);
			articleRes.setDate(article.getDate());
			
			List<String> themes = new ArrayList<>();
			for (Theme theme : article.getThemes()) {
				themes.add("#" + theme.getName());
			}			
			articleRes.setThemes(themes);
			
			articleRes.setCountLikes(article.getUsers().size());
						
			articles.add(articleRes);
		}
		
		if(articles.size() > 0) {
			return new ResponseEntity<>(articles, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND.value(), "Статьи не найдены"), HttpStatus.NOT_FOUND);
		}
	}
	
	public SuccesMessage addNewArticle(ArticleRequest articleRequest) {
		Article article = new Article();
		
		article.setTitle(articleRequest.getTitle());
		article.setText(articleRequest.getText());
		article.setImage(articleRequest.getImage());
		article.setDate(Date.valueOf(LocalDate.now()));
		
		articleRepository.save(article);
		return new SuccesMessage("Article added successfully");
	}
}
