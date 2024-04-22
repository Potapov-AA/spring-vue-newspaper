package ru.streje.newspaper.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ru.streje.newspaper.dtos.CommentRequest;
import ru.streje.newspaper.dtos.CommentResponse;
import ru.streje.newspaper.dtos.JwtRequest;
import ru.streje.newspaper.dtos.JwtResponse;
import ru.streje.newspaper.dtos.RegistrationUserRequest;
import ru.streje.newspaper.services.AuthService;
import ru.streje.newspaper.services.CommentService;


@SpringBootTest
public class CommentServiceTest {
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	AuthService authService;
	
	//TODO: Поправить тесты и проверить что он проходит
//	@Test
//	public void getCommentsForArticle_returnResponseEntity_OK() {
//				
//		ResponseEntity<?> responseEntity = commentService.getComments(1);		
//		
//		// Не должен быть пустой
//		// Должен вернуть статус 200
//		assertNotNull(responseEntity);
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//		
//	}
	
//	@Test
//	public void addComment_returnResponseEntity_OK() {
//		
//		// Добавление комментария к первой статье
//		addComment();
//		
//		// Необходимо убедится что данный комментарий добавился
//		Collection<CommentResponse> comments = (Collection<CommentResponse>) commentService.getComments(1).getBody();	
//		int targetIndex = comments.size() - 1;
//		CommentResponse comment = (CommentResponse) comments.toArray()[targetIndex];
//		
//		// Не должен быть пустым
//		// Имя владельца комментария должно соответствовать "FIRST NAME"
//		// Фамилия владельца комментария должно соответствовать "LAST NAME"
//		// Текст комментария должен соответстовать переменной "text"
//		assertEquals("FIRST NAME", comment.getFirstName());
//		assertEquals("LAST NAME", comment.getLastName());
//		assertEquals("text", comment.getText());
//	}
//	
//	
//	@Test
//	public void deleteComment_returnResponseEntity_OK() {
//		// Добавление комментария к первой статье
//		addComment();
//		
//		// Необходимо убедится что данный комментарий добавился
//		Collection<CommentResponse> comments = (Collection<CommentResponse>) commentService.getComments(1).getBody();	
//		
//		// Зафиксируем текущее количество комментариев
//		int commentCount = comments.size();
//		CommentResponse comment = (CommentResponse) comments.toArray()[commentCount-1];
//		
//		// Не должен быть пустым
//		// Имя владельца комментария должно соответствовать "FIRST NAME"
//		// Фамилия владельца комментария должно соответствовать "LAST NAME"
//		// Текст комментария должен соответстовать переменной "text"
//		assertEquals("FIRST NAME", comment.getFirstName());
//		assertEquals("LAST NAME", comment.getLastName());
//		assertEquals("text", comment.getText());
//		
//		ResponseEntity deleteResponseEntity = commentService.deleteComment(comment.getId());
//		
//		// Должен быть статус 200
//		assertEquals(HttpStatus.OK, deleteResponseEntity.getStatusCode());
//		// Количество комментариев должно уменьшиться на 1
//		Collection<CommentResponse> updateComments = (Collection<CommentResponse>) commentService.getComments(1).getBody();
//		int currentCommentSize = updateComments.size();
//		assertEquals(commentCount - 1, currentCommentSize);
//	}
//	
//	private void addComment() {
//		// Зарегестрируем пользователя для добавления комментария
//		RegistrationUserRequest registrationUserRequest = new RegistrationUserRequest();
//		registrationUserRequest.setFirstname("FIRST NAME");
//		registrationUserRequest.setLastname("LAST NAME");
//		registrationUserRequest.setPassword("testtest");
//		registrationUserRequest.setConfirmPassword("testtest");
//		registrationUserRequest.setEmail("test@test.ru");
//		
//		authService.createNewUser(registrationUserRequest);
//		
//		// Получим токен за данного пользователя
//		JwtRequest jwtRequest = new JwtRequest();
//		jwtRequest.setEmail("test@test.ru");
//		jwtRequest.setPassword("testtest");
//		JwtResponse jwtResponse = (JwtResponse) authService.createAuthToken(jwtRequest).getBody();
//		
//		// Добавим комментарий к первой статье
//		CommentRequest commentRequest = new CommentRequest();
//		commentRequest.setText("text");
//		commentService.addComment(jwtResponse.getToken(), 1, commentRequest);
//	}
}
