package ru.streje.newspaper.services.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ru.streje.newspaper.dtos.ArticleRequest;
import ru.streje.newspaper.dtos.ArticleResponse;
import ru.streje.newspaper.dtos.InfoMessageResponse;
import ru.streje.newspaper.models.Article;
import ru.streje.newspaper.models.Theme;
import ru.streje.newspaper.repositories.ArticleRepository;
import ru.streje.newspaper.repositories.ThemeRepository;
import ru.streje.newspaper.services.ArticleService;
import ru.streje.newspaper.services.ThemeService;


@Service
public class ArticleServiceImpl implements ArticleService {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private ThemeService themeService;
	
	@Autowired
	private ThemeRepository themeRepository;
	
	private final int secondsIn24Houres = 86400;

	
	/**
	 * Метод заполняет и возвращает экземпляр класса ArticleResponse
	 * 
	 * @param article - экземпляр класса Article
	 * 
	 * @return ArticleResponse
	 */
	private ArticleResponse fillArticleResponse(Article article) {
		
		ArticleResponse articleResponse = new ArticleResponse();

		articleResponse.setId(article.getId());
		articleResponse.setTitle(article.getTitle());
		articleResponse.setText(article.getText());
		articleResponse.setImage(article.getImage());
		articleResponse.setDate(article.getDate());

		List<String> themes = new ArrayList<>();
		for (Theme theme : article.getThemes()) {
			themes.add(theme.getName());
		}
		articleResponse.setThemes(themes);

		articleResponse.setCountLikes(article.getUsers().size());

		return articleResponse;
	}

	
	/**
	 * Метод заполняет коллекцию тем
	 * 
	 * @param articleRequest - параметры req-запроса
	 * 
	 * @return Collection<Theme>
	 */
	private Collection<Theme> fillThemes(ArticleRequest articleRequest) {
		
		Collection<Theme> themes = new ArrayList<>();
		for (String themeName : articleRequest.getThemes()) {
			themeName = themeName.toLowerCase();
			if (themeService.getTheme(themeName) != null) {
				Theme theme = themeService.getTheme(themeName);
				themes.add(theme);
			} else {
				themeService.addTheme(themeName);
				Theme theme = themeService.getTheme(themeName);
				themes.add(theme);
			}
		}
		return themes;
	}

	
	/**
	 * Метод возвращает все статьи при их наличии, иначе возвращает сообщение, что
	 * статьи не найдены
	 * 
	 * @return List<ArticleResponse>
	 */
	public List<ArticleResponse> getAllArticle() {
		
		List<ArticleResponse> articles = new ArrayList<>();

		Collection<Article> iArticles = articleRepository.findByOrderByIdDesc();

		for (Article article : iArticles) {
			
			Instant instantArticleDate = article.getDate().toInstant();
			Instant instantCurrentDate = new Date().toInstant();
			 
			Duration duration = Duration.between(instantArticleDate, instantCurrentDate);
			
			if (duration.getSeconds() <= secondsIn24Houres) {
			ArticleResponse articleResponse = fillArticleResponse(article);
				articles.add(articleResponse);
			}
		}
		
		return articles;
	}

	
	/**
	 * Метод поиска статьи по ID
	 * 
	 * @param id - индитификатор статьи
	 * 
	 * @return ArticleResponse / null
	 */
	public ArticleResponse getArticle(int id) {
		
		try {
			Article article = articleRepository.findById(id).get();

			ArticleResponse articleResponse = fillArticleResponse(article);

			return articleResponse;
		} catch (Exception e) {
			return null;
		}
	}

	
	/**
	 * Метод добавления новой статьи в БД
	 * 
	 * @param articleRequest - данные для новой статьи
	 * 
	 * @return InfoMessageResponse
	 */
	public InfoMessageResponse addNewArticle(ArticleRequest articleRequest) {
		
		Article article = new Article();

		article.setTitle(articleRequest.getTitle());
		article.setText(articleRequest.getText());
		article.setImage(articleRequest.getImage());
		article.setDate(new Date());
		
		Collection<Theme> themes = fillThemes(articleRequest);
		article.setThemes(themes);
		
		try {
			articleRepository.save(article);
			return new InfoMessageResponse(HttpStatus.OK.value(), "Статья успешно добавлена");
		} catch (Exception e) {
			return new InfoMessageResponse(HttpStatus.BAD_REQUEST.value(), "Не удалось добавить новую статью");
		}
	}

	
	/**
	 * Метод удаления статьи по ID
	 * 
	 * @param id - индитификатор статьи
	 * 
	 * @return InfoMessageResponse
	 */
	public InfoMessageResponse deleteArticle(int id) {
		
		try {
			Article article = articleRepository.findById(id).get();
			
			List<Theme> themes = (List<Theme>) article.getThemes();
			for (Theme theme : themes) {
				if(checkThemeCounts(theme.getId().intValue()) == 1) {
					themeRepository.delete(theme);
				}
			}
			
			articleRepository.delete(article);
			
			return new InfoMessageResponse(HttpStatus.OK.value(), "Статья успешно удалена");
		} catch (Exception e) {
			return new InfoMessageResponse(HttpStatus.NOT_FOUND.value(), "Данная статья не найдена или уже удалена");
		}
	}

	
	/**
	 * Метод обновления статьи по ID
	 * 
	 * @param articleRequest - изменяемые параметры
	 * @param id             - индитификатор статьи
	 * 
	 * @return InfoMessageResponse
	 */
	public InfoMessageResponse changeAtricle(ArticleRequest articleRequest, int id) {
		
		try {
			Article article = articleRepository.findById(id).get();

			if (articleRequest.getTitle() != null) {
				article.setTitle(articleRequest.getTitle());
			}

			if (articleRequest.getText() != null) {
				article.setText(articleRequest.getText());
			}

			article.setImage(articleRequest.getImage());

			if (articleRequest.getThemes() != null) {
				Collection<Theme> themes = fillThemes(articleRequest);
				article.setThemes(themes);
			}

			articleRepository.save(article);
			
			return new InfoMessageResponse(HttpStatus.OK.value(), "Статья успешно изменена");
		} catch (Exception e) {
			
			return new InfoMessageResponse(HttpStatus.NOT_MODIFIED.value(), "Не удалось изменить данную статью");
		}

	}
	
	
	/**
	 * Метод получения количества того сколько раз тема встречается во всех статьях
	 * 
	 * @param themeId - индитификатор темы
	 * 
	 * @return int
	 */
	private int checkThemeCounts(int themeId) {
		
		List<Article> articles = (List<Article>) articleRepository.findAll();
		
		int count = 0;
		for (Article article : articles) {
			
			List<Theme> themes = (List<Theme>) article.getThemes();
			
			for (Theme theme : themes) {
				if (theme.getId().intValue() == themeId) count++;
			}
		}
		
		return count;
	}
}
