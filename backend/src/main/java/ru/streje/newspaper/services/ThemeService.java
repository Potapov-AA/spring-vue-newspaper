package ru.streje.newspaper.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.models.LikeDislikeTheme;
import ru.streje.newspaper.models.Theme;
import ru.streje.newspaper.models.User;
import ru.streje.newspaper.repositories.LikeDislikeThemeRepository;
import ru.streje.newspaper.repositories.ThemeRepository;
import ru.streje.newspaper.utilis.JwtTokenUtils;

@Service
@RequiredArgsConstructor
public class ThemeService {
	private final ThemeRepository themeRepository;
	private final LikeDislikeThemeRepository likeDislikeThemeRepository;
	private final JwtTokenUtils jwtTokenUtils;
	private final UserService userService;

	
	/**
	 * Метод получения экземпляра Theme по переданому имени, если темы с данным
	 * именем нет, то возвращает null
	 * 
	 * @param name - название темы
	 * @return экземпляр Theme / null
	 */
	public Theme getTheme(String name) {
		try {
			Theme theme = themeRepository.findByName(name).get();
			return theme;
		} catch (Exception e) {
			return null;
		}
	}

	
	/**
	 * Метод добавления новой темы
	 * 
	 * @param name - название темы
	 */
	public void addTheme(String name) {
		Theme theme = new Theme();
		theme.setName(name);
		themeRepository.save(theme);
	}
	
	
	/***
	 * Метод получения всех тем
	 * 
	 * @return Список всех тем
	 */
	public ResponseEntity<?> getAllTheme() {
		Iterable<Theme> itThemes = themeRepository.findAll(); 
		
		List<Theme> themes  =  new ArrayList<>();
		for(Theme theme: itThemes) {
			themes.add(theme);
		}
		
		if (themes.size() > 0) {
			return new ResponseEntity<>(themes, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	/***
	 * Метод получения запретных/любимых тем пользователя
	 * 
	 * @param token  - токен авторизации
	 * @param status - статус тем (-1 - запретные темы, 1 - любимые темы)
	 * 
	 * @return List<Theme> или статус NOT_FOUND 
	 */
	public ResponseEntity<?> getUserLikesDislikeTheme(String token, Integer status) {
		
		String email = jwtTokenUtils.getUsername(token);
		User user = userService.findByEmail(email).get();
		
		Collection<LikeDislikeTheme> likeDislikeTheme = likeDislikeThemeRepository.findByUser(user);

		List<Theme> themes =  new ArrayList<>();
		for(LikeDislikeTheme theme: likeDislikeTheme) {
			if(theme.getStatus() == status) {
				themes.add(theme.getTheme());
			}
		}
		
		if (themes.size() > 0) {
			return new ResponseEntity<>(themes, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(
					HttpStatus.NOT_FOUND);
		}
	}
	
	
	/***
	 * Метод удаления статуса любимой/запретной темы
	 * 
	 * @param token   - токен авторизации
	 * @param themeId - ID темы
	 * 
	 * @return статус OK/NOT_FOUND
	 */
	public ResponseEntity<?> deleteUserLikeDislikeTheme(String token, Integer themeId) {
		String email = jwtTokenUtils.getUsername(token);
		User user = userService.findByEmail(email).get();
		
		try {
			Theme theme = themeRepository.findById(themeId).get();
			LikeDislikeTheme likeDislikeThemes = likeDislikeThemeRepository.findByUserAndTheme(user, theme).get();
			likeDislikeThemeRepository.delete(likeDislikeThemes);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	/***
	 * Метод добавления запретных/любимых тем пользователя
	 * 
	 * @param token   - токен авторизации
	 * @param themeId - ID темы
	 * @param status  - статус тем (-1 - запретные темы, 1 - любимые темы)
	 * 
	 * @return статус OK
	 */
	public ResponseEntity<?> addUserLikeDislikeTheme(String token, Integer themeId, Integer status) {
		String email = jwtTokenUtils.getUsername(token);
		User user = userService.findByEmail(email).get();
		Theme theme = themeRepository.findById(themeId).get();
		
		try {
			LikeDislikeTheme likeDislikeThemes = likeDislikeThemeRepository.findByUserAndTheme(user, theme).get();
			
			likeDislikeThemeRepository.delete(likeDislikeThemes);
			likeDislikeThemes.setStatus(status);
			likeDislikeThemeRepository.save(likeDislikeThemes);
			
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			LikeDislikeTheme likeDislikeThemes = new LikeDislikeTheme();
			likeDislikeThemes.setStatus(status);
			likeDislikeThemes.setTheme(theme);
			likeDislikeThemes.setUser(user);
			likeDislikeThemes.setThemeId(theme.getId());
			likeDislikeThemes.setUserId(user.getId());
			
			likeDislikeThemeRepository.save(likeDislikeThemes);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
	
	/***
	 * Метод проверки статуса темы, является ли она запретной/любимой
	 * 
	 * @param token   - токен авторизации
	 * @param themeId - ID темы
	 * 
	 * @return статус темы (-1 - запретные темы, 1 - любимые темы, 0 - обычная)
	 */
	public ResponseEntity<?> checkUserLikeDislikeThemeStatus(String token, Integer themeId) {
		String email = jwtTokenUtils.getUsername(token);
		User user = userService.findByEmail(email).get();
		Theme theme = themeRepository.findById(themeId).get();
		
		try {
			LikeDislikeTheme likeDislikeThemes = likeDislikeThemeRepository.findByUserAndTheme(user, theme).get();
			return new ResponseEntity<>(likeDislikeThemes.getStatus(), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(0, HttpStatus.OK);
		}
		
	}
}
