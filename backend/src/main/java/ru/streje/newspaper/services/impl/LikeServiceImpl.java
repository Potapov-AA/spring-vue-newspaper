package ru.streje.newspaper.services.impl;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ru.streje.newspaper.dtos.LikeResponse;
import ru.streje.newspaper.models.Article;
import ru.streje.newspaper.models.User;
import ru.streje.newspaper.repositories.ArticleRepository;
import ru.streje.newspaper.services.LikeService;
import ru.streje.newspaper.services.UserService;


@Service
public class LikeServiceImpl implements LikeService {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private UserService userService;

	
	/**
	 * Метод добавления/удаления лайка к статье
	 * 
	 * @param articleId - индитификатор статьи
	 * 
	 * @return LikeResponse
	 */
	@Transactional
	public LikeResponse addRemoveLike(int articleId) {
		
		LikeResponse likeResponse = new LikeResponse();
		
		Article article = articleRepository.findById(articleId).get();
		Collection<User> users = article.getUsers();

		String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User targetUser = userService.findByEmail(email).get();

		if (users.contains(targetUser)) {
			users.remove(targetUser);
			likeResponse.setCountLike(users.size());
			likeResponse.setUserStatus(-1);
		} else {
			users.add(targetUser);
			likeResponse.setCountLike(users.size());
			likeResponse.setUserStatus(1);
		}

		article.setUsers(users);
		articleRepository.save(article);
		
		return likeResponse;
	}

	
	/**
	 * Метод получения статуса лайка пользователя
	 * 
	 * @param articleId - индитификатор статьи
	 * 
	 * @return LikeResponse
	 */
	@Transactional
	public LikeResponse getUserLikeStatus(int articleId) {
		
		LikeResponse likeResponse = new LikeResponse();
		
		Article article = articleRepository.findById(articleId).get();
		Collection<User> users = article.getUsers();

		String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User targetUser = userService.findByEmail(email).get();

		likeResponse.setCountLike(users.size());

		if (users.contains(targetUser)) {
			likeResponse.setUserStatus(1);
		} else {
			likeResponse.setUserStatus(-1);
		}

		return likeResponse;
	}

	
	/**
	 * Получение количества лайков
	 * 
	 * @param articleId - индитификатор статьи
	 * 
	 * @return LikeResponse
	 */
	public LikeResponse getCountLikes(int articleId) {
		
		LikeResponse likeResponse = new LikeResponse();
		
		Article article = articleRepository.findById(articleId).get();
		Collection<User> users = article.getUsers();

		likeResponse.setCountLike(users.size());

		return likeResponse;
	}
}
