package ru.streje.newspaper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import ru.streje.newspaper.models.Article;
import ru.streje.newspaper.models.Theme;
import ru.streje.newspaper.models.User;
import ru.streje.newspaper.repositories.ArticleRepository;
import ru.streje.newspaper.services.ArticleService;
import ru.streje.newspaper.services.impl.ArticleServiceImpl;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ArticleServiceTest {
	
//	@Autowired
//	private ArticleService articleService;
//	
//	@MockBean
//	private ArticleRepository articleRepository;
//	
//	
//	@Test
//	public void getAllArticle_returnResponseEntity_OK() {
//		
//		Article article = new Article();
//		
//		Collection<Theme> themes = new ArrayList<Theme>();
//		article.setThemes(themes);
//		
//		Collection<User> users = new ArrayList<User>();
//		article.setUsers(users);
//		
//		article.setDate(new Date());
//		
//		Collection<Article> iArticles = new ArrayList<Article>();
//		iArticles.add(article);
//		
//		doReturn(iArticles)
//			.when(articleRepository)
//			.findByOrderByIdDesc();
//		
//		ResponseEntity<?> responseEntity = articleService.getAllArticle();
//		
//		assertNotNull(responseEntity);
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//		
//		verify(articleRepository, times(1)).findByOrderByIdDesc();
//	}
//	
//	
//	@Test
//	public void getAllArticle_returnResponseEntity_NOT_FOUND() {
//		
//		Collection<Article> iArticles = new ArrayList<Article>();
//		
//		doReturn(iArticles)
//		.when(articleRepository)
//		.findByOrderByIdDesc();
//		
//		ResponseEntity<?> responseEntity = articleService.getAllArticle();
//		
//		assertNotNull(responseEntity);
//		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//		
//		verify(articleRepository, times(1)).findByOrderByIdDesc();
//	}
//	
//	
//	@Test
//	public void getArticleResponse_returnResponseEntity_OK() {
//		
//		Article article = new Article();
//		
//		Collection<Theme> themes = new ArrayList<Theme>();
//		article.setThemes(themes);
//		
//		Collection<User> users = new ArrayList<User>();
//		article.setUsers(users);
//		
//		article.setDate(new Date());
//		
//		Optional<Article> oArticle = Optional.of(article);
//		
//		doReturn(oArticle)
//		.when(articleRepository)
//		.findById(1);
//		
//		ResponseEntity<?> responseEntity = articleService.getArticleResponse(1);
//		
//		assertNotNull(responseEntity);
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//		
//		verify(articleRepository, times(1)).findById(1);
//	}
//	
//	
//	@Test
//	public void getArticleResponse_returnResponseEntity_NOT_FOUND() {
//		
//		doReturn(Optional.empty())
//		.when(articleRepository)
//		.findById(2);
//		
//		ResponseEntity<?> responseEntity = articleService.getArticleResponse(2);
//		
//		assertNotNull(responseEntity);
//		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//		
//		verify(articleRepository, times(1)).findById(2);
//	}
//	
//	
//	@Test
//	public void addNewArticle_returnResponseEntity() {
//		
//		ArticleRequest articleRequest = new ArticleRequest();
//		
//		String[] themes = new String[] {"тема 1", "тема 2"};
//		articleRequest.setThemes(themes);
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
//		Article article = new Article();
//		
//		Collection<Theme> themes = new ArrayList<Theme>();
//		article.setThemes(themes);
//		
//		Collection<User> users = new ArrayList<User>();
//		article.setUsers(users);
//		
//		article.setDate(new Date());
//		
//		Optional<Article> oArticle = Optional.of(article);
//		
//		doReturn(oArticle)
//		.when(articleRepository)
//		.findById(1);
//		
//		
//		ResponseEntity<?> responseEntity = articleService.deleteArticle(1);
//		
//		assertNotNull(responseEntity);
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//	}
//	
//	
//	@Test
//	public void changeAtricle_returnResponseEntity() {
//		
//		Article article = new Article();
//		
//		Collection<Theme> themes = new ArrayList<Theme>();
//		article.setThemes(themes);
//		
//		Collection<User> users = new ArrayList<User>();
//		article.setUsers(users);
//		
//		article.setDate(new Date());
//		
//		Optional<Article> oArticle = Optional.of(article);
//		
//		doReturn(oArticle)
//		.when(articleRepository)
//		.findById(1);
//		
//		ArticleRequest articleRequest = new ArticleRequest();
//		articleRequest.setImage("image");
//		articleRequest.setText("text");
//		articleRequest.setThemes(new String[] {"тема 1", "тема 2"});
//		articleRequest.setTitle("Заголовок");
//		
//		ResponseEntity<?> responseEntity = articleService.changeAtricle(articleRequest, 1);
//		
//		assertNotNull(responseEntity);
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//	}
//	
//	
//	@Test
//	public void getArticle_returnArticle() {
//		
//		Article article_test = new Article();
//		article_test.setId(1);
//		article_test.setImage("image");
//		article_test.setText("text");
//		
//		Optional<Article> oArticle = Optional.of(article_test);
//
//		doReturn(oArticle)
//		.when(articleRepository)
//		.findById(1);
//		
//		Article article = articleService.getArticle(1);
//		
//		assertNotNull(article);
//		assertEquals(1, article.getId());
//		assertEquals("image", article.getImage());
//		assertEquals("text", article.getText());
//	}
}