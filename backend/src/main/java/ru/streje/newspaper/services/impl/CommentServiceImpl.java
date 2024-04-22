package ru.streje.newspaper.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.dtos.CommentRequest;
import ru.streje.newspaper.dtos.CommentResponse;
import ru.streje.newspaper.dtos.InfoMessage;
import ru.streje.newspaper.messages.ErrorMessage;
import ru.streje.newspaper.messages.SuccesMessage;
import ru.streje.newspaper.models.Article;
import ru.streje.newspaper.models.Comment;
import ru.streje.newspaper.models.User;
import ru.streje.newspaper.repositories.CommentRepository;
import ru.streje.newspaper.services.ArticleService;
import ru.streje.newspaper.services.CommentService;
import ru.streje.newspaper.services.UserService;
import ru.streje.newspaper.utilis.JwtTokenUtils;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	private final CommentRepository commentRepository;
	private final JwtTokenUtils jwtTokenUtils;
	private final ArticleService articleService;
	private final UserService userService;

	
	/**
	 * Метод получения комментариев статьи
	 * 
	 * @param articleId - индитификатор статьи
	 * @return List<CommentResponse>
	 */
	@Transactional
	public List<CommentResponse> getComments(int articleId) {

		Article article = articleService.getArticle(articleId);
		Collection<Comment> comments = commentRepository.findByArticle(article);

		List<CommentResponse> commentResponses = new ArrayList<>();

		for (Comment comment : comments) {
			CommentResponse commentResponse = new CommentResponse();
			commentResponse.setId(comment.getId());
			commentResponse.setFirstName(comment.getUser().getFirstname());
			commentResponse.setLastName(comment.getUser().getLastname());
			commentResponse.setDate(comment.getDate());
			commentResponse.setText(comment.getText());
			commentResponses.add(commentResponse);
		}
		
		return commentResponses;
	}

	
	//TODO: Поправить момент с обращением к несуществующей статьи
	/**
	 * Метод добавления комментариев
	 * 
	 * @param token          - токен авторизации
	 * @param articleId      - индитификатор статьи
	 * @param commentRequest - параметры запроса
	 * @return сообщение о успешности или провале добавления комментария
	 */
	@Transactional
	public InfoMessage addComment(String token, int articleId, CommentRequest commentRequest) {
		Comment comment = new Comment();

		String email = jwtTokenUtils.getUsername(token);
		User user = userService.findByEmail(email).get();
		Article article = articleService.getArticle(articleId);

		comment.setArticle(article);
		comment.setText(commentRequest.getText());
		comment.setDate(new Date());
		comment.setUser(user);

		try {
			commentRepository.save(comment);
			return new InfoMessage(HttpStatus.CREATED.value(), "Комментарий успешно добавлен");
		} catch (Exception e) {
			return new InfoMessage(HttpStatus.BAD_REQUEST.value(), "Не удалось добавить новый комментарий");
		}
	}
	
	
	/***
	 * Метод удаления комментариев
	 * 
	 * @param commentId - индификатор комментария
	 * @return сообщение о успешности или провале удаления комментария
	 */
	public ResponseEntity<?> deleteComment(int commentId) {
		try {
			Comment comment = commentRepository.findById(commentId).get();
			commentRepository.delete(comment);
			return new ResponseEntity<>(new SuccesMessage("Комментарий успешно удалена"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorMessage(HttpStatus.NOT_FOUND.value(), "Данный комментарий не найден или уже удален"),
					HttpStatus.NOT_FOUND);
		}
	}
}
