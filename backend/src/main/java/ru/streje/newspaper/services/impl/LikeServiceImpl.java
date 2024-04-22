package ru.streje.newspaper.services.impl;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.dtos.LikeResponse;
import ru.streje.newspaper.models.Article;
import ru.streje.newspaper.models.User;
import ru.streje.newspaper.services.ArticleService;
import ru.streje.newspaper.services.LikeService;
import ru.streje.newspaper.services.UserService;
import ru.streje.newspaper.utilis.JwtTokenUtils;


@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
	private final ArticleService articleService;
	private final UserService userService;
	private final JwtTokenUtils jwtTokenUtils;

	
	/**
	 * Метод добавления/удаления лайка к статье
	 * 
	 * @param token     - токен авторизации, для получения данных о пользователе
	 * @param articleId - индитификатор статьи
	 * 
	 * @return LikeResponse
	 */
	@Transactional
	public LikeResponse addRemoveLike(String token, int articleId) {
		
		LikeResponse likeResponse = new LikeResponse();
		
		//TODO: Поправить вызов
		Article article = articleService.getArticle(articleId);
		Collection<User> users = article.getUsers();

		String email = jwtTokenUtils.getUsername(token);
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
		articleService.saveArticle(article);
		
		return likeResponse;
	}

	
	/**
	 * Метод получения статуса лайка пользователя
	 * 
	 * @param token     - токен авторизации, для получения данных о пользователе
	 * @param articleId - индитификатор статьи
	 * 
	 * @return LikeResponse
	 */
	@Transactional
	public LikeResponse getUserLikeStatus(String token, int articleId) {
		
		LikeResponse likeResponse = new LikeResponse();
		
		//TODO: Поправить вызов
		Article article = articleService.getArticle(articleId);
		Collection<User> users = article.getUsers();

		String email = jwtTokenUtils.getUsername(token);
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
		
		//TODO: Поправить вызов
		Article article = articleService.getArticle(articleId);
		Collection<User> users = article.getUsers();

		likeResponse.setCountLike(users.size());

		return likeResponse;
	}
}
