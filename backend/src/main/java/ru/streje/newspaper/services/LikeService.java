package ru.streje.newspaper.services;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.dtos.LikeResponse;
import ru.streje.newspaper.models.Article;
import ru.streje.newspaper.models.User;
import ru.streje.newspaper.utilis.JwtTokenUtils;

@Service
@RequiredArgsConstructor
public class LikeService {
	private final ArticleService articleService;
	private final UserService userService;
	private final JwtTokenUtils jwtTokenUtils;

	/**
	 * Метод добавления/удаления лайков к статье
	 * 
	 * @param token     - токен авторизации, для получения данных о пользователе
	 * @param articleId - индитификатор статьи
	 * @return LikeRespone(countLike:int, userStatus:int)
	 */
	public ResponseEntity<?> addRemoveLike(String token, int articleId) {
		LikeResponse likeResponse = new LikeResponse();

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

		return new ResponseEntity<>(likeResponse, HttpStatus.OK);
	}

	/**
	 * Метод получения статуса лайка пользователя
	 * 
	 * @param token     - токен авторизации, для получения данных о пользователе
	 * @param articleId - индитификатор статьи
	 * @return LikeRespone(countLike:int, userStatus:int)
	 */
	public ResponseEntity<?> getUserLikeStatus(String token, int articleId) {
		LikeResponse likeResponse = new LikeResponse();

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

		return new ResponseEntity<>(likeResponse, HttpStatus.OK);
	}

	/**
	 * Получение количества лайков
	 * 
	 * @param articleId - индитификатор статьи
	 * @return LikeRespone(countLike:int, userStatus:-1 (доступно не авторизованым
	 *         пользователям))
	 */
	public ResponseEntity<?> getCountLikes(int articleId) {
		LikeResponse likeResponse = new LikeResponse();

		Article article = articleService.getArticle(articleId);
		Collection<User> users = article.getUsers();

		likeResponse.setCountLike(users.size());

		return new ResponseEntity<>(likeResponse, HttpStatus.OK);
	}
}
