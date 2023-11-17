package ru.streje.newspaper.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.dtos.ArticleResponse;
import ru.streje.newspaper.models.Article;
import ru.streje.newspaper.models.Theme;
import ru.streje.newspaper.repositories.ArticleRepository;

@Service
@RequiredArgsConstructor
public class ArticleService {
	private final ArticleRepository articleRepository;
	
	public List<ArticleResponse> getAllArticle() {
		List<ArticleResponse> articles = new ArrayList<>();
		
		Iterable<Article> iArticles = articleRepository.findAll();
		
		for (Article article : iArticles) {
			ArticleResponse articleRes = new ArticleResponse();
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
		
		return articles;
	}
}
