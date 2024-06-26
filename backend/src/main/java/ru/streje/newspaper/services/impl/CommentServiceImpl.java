package ru.streje.newspaper.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ru.streje.newspaper.dtos.CommentRequest;
import ru.streje.newspaper.dtos.CommentResponse;
import ru.streje.newspaper.dtos.InfoMessageResponse;
import ru.streje.newspaper.models.Article;
import ru.streje.newspaper.models.Comment;
import ru.streje.newspaper.models.User;
import ru.streje.newspaper.repositories.ArticleRepository;
import ru.streje.newspaper.repositories.CommentRepository;
import ru.streje.newspaper.services.CommentService;
import ru.streje.newspaper.services.UserService;


@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private UserService userService;

	
	/**
	 * Метод получения комментариев статьи
	 * 
	 * @param articleId - индитификатор статьи
	 * 
	 * @return List<CommentResponse>
	 */
	@Transactional
	public List<CommentResponse> getComments(int articleId) {

		Article article = articleRepository.findById(articleId).get();
		Collection<Comment> comments = commentRepository.findByArticleId(article.getId());

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

	
	/**
	 * Метод добавления комментария
	 * 
	 * @param articleId      - индитификатор статьи
	 * @param commentRequest - параметры запроса
	 * 
	 * @return InfoMessageResponse
	 */
	@Transactional
	public InfoMessageResponse addComment(int articleId, CommentRequest commentRequest) {
		
		Comment comment = new Comment();

		String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User user = userService.findByEmail(email).get();
		
		comment.setArticle(articleRepository.findById(articleId).get());
		comment.setText(commentRequest.getText());
		comment.setDate(new Date());
		comment.setUser(user);

		try {
			commentRepository.save(comment);
			return new InfoMessageResponse(HttpStatus.CREATED.value(), "Комментарий успешно добавлен");
		} catch (Exception e) {
			return new InfoMessageResponse(HttpStatus.BAD_REQUEST.value(), "Не удалось добавить новый комментарий");
		}
	}
	
	
	/***
	 * Метод удаления комментария
	 * 
	 * @param commentId - индификатор комментария
	 * 
	 * @return InfoMessageResponse
	 */
	public InfoMessageResponse deleteComment(int commentId) {
		
		try {
			Comment comment = commentRepository.findById(commentId).get();
			commentRepository.delete(comment);
			return new InfoMessageResponse(HttpStatus.OK.value(), "Комментарий успешно удален");
		} catch (Exception e) {
			return new InfoMessageResponse(HttpStatus.NOT_FOUND.value(), "Данный комментарий не найден или уже удален");
		}
	}
}
