package ru.streje.newspaper.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.dtos.ArticleRequest;
import ru.streje.newspaper.dtos.ArticleResponse;
import ru.streje.newspaper.messages.ErrorMessage;
import ru.streje.newspaper.messages.SuccesMessage;
import ru.streje.newspaper.models.Article;
import ru.streje.newspaper.models.Theme;
import ru.streje.newspaper.repositories.ArticleRepository;

@Service
@RequiredArgsConstructor
public class ArticleService {
	private final ArticleRepository articleRepository;

	private final ThemeService themeService;

	private final int secondsIn24Houres = 86400;

	/**
	 * Метод заполняет и возвращает экземпляр класса ArticleResponse
	 * 
	 * @param article - экземпляр класса Article
	 * @return экземпляр класса ArticleResponse
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
	 * @return Collection<Theme>
	 */
	private Collection<Theme> fillThemes(ArticleRequest articleRequest) {
		Collection<Theme> themes = new ArrayList<>();
		for (String themeName : articleRequest.getThemes()) {
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
	 * @return список всех статей (ArticleResponse) / сообщение об их отсутствии
	 */
	public ResponseEntity<?> getAllArticle() {
		List<ArticleResponse> articles = new ArrayList<>();

		Iterable<Article> iArticles = articleRepository.findByOrderByIdDesc();

		for (Article article : iArticles) {
			//// УБРАТЬ КОММЕНТАРИИ КОГДА БУДЕТ ПОЛНОСТЬЮ ГОТОВ ФУНКЦИОНАЛ
//			if ((new Date().getTime() - article.getDate().getTime()) / 100 <= secondsIn24Houres) {
			ArticleResponse articleResponse = fillArticleResponse(article);
			articles.add(articleResponse);
//			}
		}

		if (articles.size() > 0) {
			return new ResponseEntity<>(articles, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(
					new ErrorMessage(HttpStatus.NOT_FOUND.value(), "За последнии 24 часа статьей не найдено"),
					HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Метод поиска статьи по ID
	 * 
	 * @param id - индитификатор статьи
	 * @return статью или сообщение о ее отсутствии
	 */
	public ResponseEntity<?> getArticleResponse(int id) {
		try {
			Article article = articleRepository.findById(id).get();

			ArticleResponse articleResponse = fillArticleResponse(article);

			return new ResponseEntity<>(articleResponse, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND.value(), "Данная статья не найдена"),
					HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Метод добавления новой статьи в БД
	 * 
	 * @param articleRequest - данные для новой статьи
	 * @return сообщение об успехе/провале добавления статьи
	 */
	public ResponseEntity<?> addNewArticle(ArticleRequest articleRequest) {
		Article article = new Article();

		article.setTitle(articleRequest.getTitle());
		article.setText(articleRequest.getText());
		article.setImage(articleRequest.getImage());
		article.setDate(new Date());
		
		Collection<Theme> themes = fillThemes(articleRequest);
		article.setThemes(themes);
		
		try {
			articleRepository.save(article);
			return new ResponseEntity<>(new SuccesMessage("Статья успешно добавлена"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Не удалось добавить новую статью"),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Метод удаления статьи по ID
	 * 
	 * @param id - индитификатор статьи
	 * @return сообщение об успехе/провале удаления статьи
	 */
	public ResponseEntity<?> deleteArticle(int id) {
		try {
			Article article = articleRepository.findById(id).get();
			articleRepository.delete(article);
			return new ResponseEntity<>(new SuccesMessage("Статья успешно удалена"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorMessage(HttpStatus.NOT_FOUND.value(), "Данная статья не найдена или уже удалена"),
					HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Метод обновления статьи по ID
	 * 
	 * @param articleRequest - изменяемые параметры
	 * @param id             - индитификатор статьи
	 * @return сообщение об успехе/провале обновления статьи
	 */
	public ResponseEntity<?> changeAtricle(ArticleRequest articleRequest, int id) {
		try {
			Article article = articleRepository.findById(id).get();

			if (articleRequest.getTitle() != null) {
				article.setTitle(articleRequest.getTitle());
			}

			if (articleRequest.getText() != null) {
				article.setText(articleRequest.getText());
			}

			if (articleRequest.getImage() != null) {
				article.setImage(articleRequest.getImage());
			}

			if (articleRequest.getThemes() != null) {
				Collection<Theme> themes = fillThemes(articleRequest);
				article.setThemes(themes);
			}

			articleRepository.save(article);

			return new ResponseEntity<>(new SuccesMessage("Статья успешно изменена"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorMessage(HttpStatus.NOT_MODIFIED.value(), "Не удалось изменить данную статью"),
					HttpStatus.NOT_MODIFIED);
		}

	}

	/**
	 * Метод получение экземпляра Article по id
	 * 
	 * @param id - индитификатор
	 * @return экземпляр Article
	 */
	public Article getArticle(int id) {
		try {
			Article article = articleRepository.findById(id).get();
			return article;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Метод сохранения экземпляра Article в БД
	 * 
	 * @param article - экземпляр Article
	 */
	public void saveArticle(Article article) {
		try {
			articleRepository.save(article);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
