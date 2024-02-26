package ru.streje.newspaper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.streje.newspaper.dtos.ArticleRequest;
import ru.streje.newspaper.dtos.ArticleResponse;
import ru.streje.newspaper.messages.ErrorMessage;
import ru.streje.newspaper.models.Article;
import ru.streje.newspaper.models.Theme;
import ru.streje.newspaper.models.User;
import ru.streje.newspaper.repositories.ArticleRepository;
import ru.streje.newspaper.services.ArticleService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ArticleServiceTest {
	
	@Autowired
	private ArticleService articleService;
	
	@MockBean
	private ArticleRepository articleRepository;
	
	
	@Test
	public void getAllArticle_returnResponseEntity_OK() {
		
		Article article = new Article();
		
		Collection<Theme> themes = new ArrayList<Theme>();
		article.setThemes(themes);
		
		Collection<User> users = new ArrayList<User>();
		article.setUsers(users);
		
		article.setDate(new Date());
		
		Collection<Article> iArticles = new ArrayList<Article>();
		iArticles.add(article);
		
		doReturn(iArticles)
			.when(articleRepository)
			.findByOrderByIdDesc();
		
		ResponseEntity<?> responseEntity = articleService.getAllArticle();
		
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		verify(articleRepository, times(1)).findByOrderByIdDesc();
	}
	
	
	@Test
	public void getAllArticle_returnResponseEntity_NOT_FOUND() {
		
		Collection<Article> iArticles = new ArrayList<Article>();
		
		doReturn(iArticles)
		.when(articleRepository)
		.findByOrderByIdDesc();
		
		ResponseEntity<?> responseEntity = articleService.getAllArticle();
		
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		
		verify(articleRepository, times(1)).findByOrderByIdDesc();
	}
	
	
	@Test
	public void getArticleResponse_returnResponseEntity_OK() {
		
		Article article = new Article();
		
		Collection<Theme> themes = new ArrayList<Theme>();
		article.setThemes(themes);
		
		Collection<User> users = new ArrayList<User>();
		article.setUsers(users);
		
		article.setDate(new Date());
		
		Optional<Article> oArticle = Optional.of(article);
		
		doReturn(oArticle)
		.when(articleRepository)
		.findById(1);
		
		ResponseEntity<?> responseEntity = articleService.getArticleResponse(1);
		
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		verify(articleRepository, times(1)).findById(1);
	}
	
	
	@Test
	public void getArticleResponse_returnResponseEntity_NOT_FOUND() {
		
		doReturn(Optional.empty())
		.when(articleRepository)
		.findById(2);
		
		ResponseEntity<?> responseEntity = articleService.getArticleResponse(2);
		
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		
		verify(articleRepository, times(1)).findById(2);
	}
	
	
//	@Test
//	public void addNewArticle_returnResponseEntity() {
//		
//		ArticleRequest articleRequest = new ArticleRequest();
//		articleRequest.setImage("image");
//		articleRequest.setText("text");
//		
//		String[] themes = new String[] {"тема 1", "тема 2"};
//		articleRequest.setThemes(themes);
//		
//		articleRequest.setTitle("Заголовок");
//		
//		ResponseEntity<?> responseEntity = articleService.addNewArticle(articleRequest);
//		
//		assertNotNull(responseEntity);
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//	}
//	
//	
//	@Test
//	public void deleteArticle_returnResponseEntity() {
//		
//		ResponseEntity<?> responseEntity = articleService.deleteArticle(1);
//		
//		assertNotNull(responseEntity);
//		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//	}
//	
//	
//	@Test
//	public void changeAtricle_returnResponseEntity() {
//		
//		ArticleRequest articleRequest = new ArticleRequest();
//		articleRequest.setImage("image");
//		articleRequest.setText("text");
//		
//		String[] themes = new String[] {"тема 1", "тема 2"};
//		articleRequest.setThemes(themes);
//		
//		articleRequest.setTitle("Заголовок");
//		
//		ResponseEntity<?> responseEntity = articleService.changeAtricle(articleRequest, 1);
//		
//		assertNotNull(responseEntity);
//		assertEquals(HttpStatus.NOT_MODIFIED, responseEntity.getStatusCode());
//	}
}




//Article article = new Article();
//
//Theme theme1 = new Theme();
//theme1.setId(1);
//theme1.setName("Тема 1");
//
//Theme theme2 = new Theme();
//theme1.setId(2);
//theme1.setName("Тема 2");
//
//User user1 = new User();
//User user2 = new User();
//
//Collection<User> users = new ArrayList<User>();
//users.add(user1);
//users.add(user2);
//
//Collection<Theme> themes = new ArrayList<Theme>();
//themes.add(theme1);
//themes.add(theme2);
//
//article.setId(1);
//article.setTitle("Заголовок");
//article.setImage("image test");
//article.setText("Текст");
//article.setThemes(themes);
//article.setUsers(users);
//article.setDate(new Date(1212121212121L));