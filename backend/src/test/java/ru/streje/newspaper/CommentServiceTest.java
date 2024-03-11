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

import ru.streje.newspaper.dtos.CommentRequest;
import ru.streje.newspaper.models.Article;
import ru.streje.newspaper.models.Comment;
import ru.streje.newspaper.models.User;
import ru.streje.newspaper.repositories.CommentRepository;
import ru.streje.newspaper.services.ArticleService;
import ru.streje.newspaper.services.CommentService;
import ru.streje.newspaper.services.UserService;
import ru.streje.newspaper.services.impl.ArticleServiceImpl;
import ru.streje.newspaper.utilis.JwtTokenUtils;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CommentServiceTest {
	
	@Autowired
	private CommentService commentService;
	
	@MockBean
	CommentRepository commentRepository;
	
	@MockBean
	ArticleService articleService;
	
	@MockBean
	JwtTokenUtils jwtTokenUtils;
	
	@MockBean
	UserService userService;
	
	
	@Test
	public void getComments_returnResponseEntity_OK() {
		
		Article article = new Article();
		
		doReturn(article)
			.when(articleService)
			.getArticle(1);
		
		Comment comment = new Comment();
		comment.setUser(new User());
		
		Collection<Comment> comments = new ArrayList<Comment>();
		comments.add(comment);
		
		doReturn(comments)
			.when(commentRepository)
			.findByArticle(article);
		
		ResponseEntity<?> responseEntity = commentService.getComments(1);
		
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	
	@Test
	public void addComment_returnResponseEntity_OK() {
		
		String mail = "mail@mail.ru";
		
		doReturn(mail)
			.when(jwtTokenUtils)
			.getUsername("token");
		
		User user = new User();
		
		doReturn(Optional.of(user))
			.when(userService)
			.findByEmail(mail);
		
		Article article = new Article();
		
		doReturn(article)
			.when(articleService)
			.getArticle(1);
		
		CommentRequest commentRequest = new CommentRequest();
		
		ResponseEntity<?> responseEntity = commentService.addComment("token", 1, commentRequest);
		
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	
	@Test
	public void deleteComment_returnResponseEntity_OK() {
		
		Comment comment = new Comment();
		
		doReturn(Optional.of(comment))
			.when(commentRepository)
			.findById(1);
		
		ResponseEntity<?> responseEntity = commentService.deleteComment(1);
		
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}
