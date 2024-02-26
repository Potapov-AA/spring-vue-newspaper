package ru.streje.newspaper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.streje.newspaper.models.Article;
import ru.streje.newspaper.models.User;
import ru.streje.newspaper.services.ArticleService;
import ru.streje.newspaper.services.LikeService;
import ru.streje.newspaper.services.UserService;
import ru.streje.newspaper.utilis.JwtTokenUtils;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class LikeServiceTest {
	
	@Autowired
	LikeService likeService;
	
	@MockBean
	ArticleService articleService;
	
	@MockBean
	JwtTokenUtils jwtTokenUtils;
	
	@MockBean
	UserService userService;
	
	
	@Test
	public void addRemoveLike_returnResponseEntity_OK() {
		
		User user = new User();
		Collection<User> users = new ArrayList<User>();
		users.add(user);
		
		Article article = new Article();
		article.setUsers(users);
		
		doReturn(article)
			.when(articleService)
			.getArticle(1);
		
		String mail = "mail@mail.ru";
		
		doReturn(mail)
			.when(jwtTokenUtils)
			.getUsername("token");
		
		doReturn(Optional.of(user))
			.when(userService)
			.findByEmail(mail);
		
		ResponseEntity<?> responseEntity = likeService.addRemoveLike("token", 1);
				
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	
	@Test
	public void getUserLikeStatus_returnResponseEntity_OK() {
		
		User user = new User();
		Collection<User> users = new ArrayList<User>();
		users.add(user);
		
		Article article = new Article();
		article.setUsers(users);
		
		doReturn(article)
			.when(articleService)
			.getArticle(1);
		
		String mail = "mail@mail.ru";
		
		doReturn(mail)
			.when(jwtTokenUtils)
			.getUsername("token");
		
		doReturn(Optional.of(user))
			.when(userService)
			.findByEmail(mail);
		
		ResponseEntity<?> responseEntity = likeService.getUserLikeStatus("token", 1);
		
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	
	@Test
	public void getCountLikes_returnResponseEntity_OK() {
		
		User user = new User();
		Collection<User> users = new ArrayList<User>();
		users.add(user);
		
		Article article = new Article();
		article.setUsers(users);
		
		doReturn(article)
			.when(articleService)
			.getArticle(1);
		
		ResponseEntity<?> responseEntity = likeService.getCountLikes(1);
		
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}
