package ru.streje.newspaper.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import ru.streje.newspaper.dtos.CommentRequest;
import ru.streje.newspaper.dtos.CommentResponse;
import ru.streje.newspaper.dtos.InfoMessageResponse;
import ru.streje.newspaper.dtos.JwtRequest;
import ru.streje.newspaper.dtos.JwtResponse;
import ru.streje.newspaper.dtos.RegistrationUserRequest;
import ru.streje.newspaper.services.AuthService;
import ru.streje.newspaper.services.CommentService;
import ru.streje.newspaper.utilis.JwtTokenUtils;


@SpringBootTest
public class CommentServiceTest {
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	AuthService authService;
	
	@Autowired
	JwtTokenUtils jwtTokenUtils;
	
	//TODO: Поправить тесты и проверить что он проходит
	@Test
	public void getCommentsForArticle_returnResponseEntity_OK() {
				
		List<CommentResponse> commentResponses = commentService.getComments(1);		
		
		// Не должен быть пустой
		assertNotNull(commentResponses);
	}
	
	@Test
	public void addComment_returnResponseEntity_OK() {
		
		// Добавление комментария к первой статье
		addComment();
		
		// Необходимо убедится что данный комментарий добавился
		Collection<CommentResponse> comments = (Collection<CommentResponse>) commentService.getComments(1);	
		int targetIndex = comments.size() - 1;
		CommentResponse comment = (CommentResponse) comments.toArray()[targetIndex];
		
		// Не должен быть пустым
		// Имя владельца комментария должно соответствовать "FIRST NAME"
		// Фамилия владельца комментария должно соответствовать "LAST NAME"
		// Текст комментария должен соответстовать переменной "text"
		assertEquals("FIRST NAME", comment.getFirstName());
		assertEquals("LAST NAME", comment.getLastName());
		assertEquals("text", comment.getText());
	}
	
	
	@Test
	public void deleteComment_returnResponseEntity_OK() {
		// Добавление комментария к первой статье
		addComment();
		
		// Необходимо убедится что данный комментарий добавился
		Collection<CommentResponse> comments = (Collection<CommentResponse>) commentService.getComments(1);	
		
		// Зафиксируем текущее количество комментариев
		int commentCount = comments.size();
		CommentResponse comment = (CommentResponse) comments.toArray()[commentCount-1];
		
		// Не должен быть пустым
		// Имя владельца комментария должно соответствовать "FIRST NAME"
		// Фамилия владельца комментария должно соответствовать "LAST NAME"
		// Текст комментария должен соответстовать переменной "text"
		assertEquals("FIRST NAME", comment.getFirstName());
		assertEquals("LAST NAME", comment.getLastName());
		assertEquals("text", comment.getText());
		
		InfoMessageResponse deleteResponseEntity = commentService.deleteComment(comment.getId());
		
		// Должен быть статус 200
		assertEquals(HttpStatus.OK.value(), deleteResponseEntity.getStatus());
		// Количество комментариев должно уменьшиться на 1
		Collection<CommentResponse> updateComments = (Collection<CommentResponse>) commentService.getComments(1);
		int currentCommentSize = updateComments.size();
		assertEquals(commentCount - 1, currentCommentSize);
	}
	
	private void addComment() {
		// Зарегестрируем пользователя для добавления комментария
		RegistrationUserRequest registrationUserRequest = new RegistrationUserRequest();
		registrationUserRequest.setFirstname("FIRST NAME");
		registrationUserRequest.setLastname("LAST NAME");
		registrationUserRequest.setPassword("testtest");
		registrationUserRequest.setConfirmPassword("testtest");
		registrationUserRequest.setEmail("test@test.ru");
		
		authService.createNewUser(registrationUserRequest);
		
		// Получим токен за данного пользователя
		JwtRequest jwtRequest = new JwtRequest();
		jwtRequest.setEmail("test@test.ru");
		jwtRequest.setPassword("testtest");
		JwtResponse jwtResponse = (JwtResponse) authService.createAuthToken(jwtRequest);
		
		// Проведем авторизацию за пользователя
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(jwtTokenUtils.getUsername(jwtResponse.getToken()), null,
				jwtTokenUtils.getRoles(jwtResponse.getToken()).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		
		SecurityContextHolder.getContext().setAuthentication(token);
		
		// Добавим комментарий к первой статье
		CommentRequest commentRequest = new CommentRequest();
		commentRequest.setText("text");
		commentService.addComment(1, commentRequest);
	}
}
