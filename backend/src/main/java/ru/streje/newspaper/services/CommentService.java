package ru.streje.newspaper.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.dtos.CommentRequest;
import ru.streje.newspaper.dtos.CommentResponse;
import ru.streje.newspaper.messages.ErrorMessage;
import ru.streje.newspaper.messages.SuccesMessage;
import ru.streje.newspaper.models.Article;
import ru.streje.newspaper.models.Comment;
import ru.streje.newspaper.models.User;
import ru.streje.newspaper.repositories.CommentRepository;
import ru.streje.newspaper.utilis.JwtTokenUtils;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final JwtTokenUtils jwtTokenUtils;
	private final ArticleService articleService;
	private final UserService userService;

	/**
	 * Метод получения всех комментариев определенной статьи
	 * @param articleId - индитификатор статьи
	 * @return CommentResponse или сообщение о отсутствии комментариев
	 */
	public ResponseEntity<?> getComments(int articleId) {

		Article article = articleService.getArticle(articleId);
		Collection<Comment> comments = commentRepository.findByArticle(article);

		List<CommentResponse> commentResponses = new ArrayList<>();

		for (Comment comment : comments) {
			CommentResponse commentResponse = new CommentResponse();
			commentResponse.setFirstName(comment.getUser().getFirstname());
			commentResponse.setLastName(comment.getUser().getLastname());
			commentResponse.setDate(Date.valueOf(LocalDate.now()));
			commentResponse.setText(comment.getText());
			commentResponses.add(commentResponse);
		}

		if (commentResponses.size() > 0) {
			return new ResponseEntity<>(commentResponses, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND.value(), "Комментарии не найдены"),
					HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Метод добавления комментариев
	 * @param token - токен авторизации
	 * @param articleId - индитификатор статьи
	 * @param commentRequest - параметры запроса
	 * @return сообщение о успешности или провале добавления комментария
	 */
	public ResponseEntity<?> addComment(String token, int articleId, CommentRequest commentRequest) {
		Comment comment = new Comment();

		String email = jwtTokenUtils.getUsername(token);
		User user = userService.findByEmail(email).get();
		Article article = articleService.getArticle(articleId);
		
		comment.setArticle(article);
		comment.setText(commentRequest.getText());
		comment.setDate(Date.valueOf(LocalDate.now()));
		comment.setUser(user);

		try {
			commentRepository.save(comment);
			return new ResponseEntity<>(new SuccesMessage("Комментарий успешно добавлен"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Не удалось добавить новый комментарий"),
					HttpStatus.BAD_REQUEST);
		}
	}
}
