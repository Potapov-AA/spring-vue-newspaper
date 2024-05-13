package ru.streje.newspaper.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ru.streje.newspaper.dtos.InfoMessageResponse;
import ru.streje.newspaper.dtos.LikeDislikeThemeResponse;
import ru.streje.newspaper.models.LikeDislikeTheme;
import ru.streje.newspaper.models.Theme;
import ru.streje.newspaper.models.User;
import ru.streje.newspaper.repositories.LikeDislikeThemeRepository;
import ru.streje.newspaper.repositories.ThemeRepository;
import ru.streje.newspaper.services.ThemeService;
import ru.streje.newspaper.services.UserService;


@Service
public class ThemeServiceImpl implements ThemeService {
	
	@Autowired
	private ThemeRepository themeRepository;
	
	@Autowired
	private LikeDislikeThemeRepository likeDislikeThemeRepository;
	
	@Autowired
	private UserService userService;

	
	/**
	 * Метод получения экземпляра Theme по переданому имени, если темы с данным
	 * именем нет, то возвращает null
	 * 
	 * @param name - название темы
	 * 
	 * @return Theme / null
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
	 * Метод получения списка всех тем
	 * 
	 * @return List<Theme>
	 */
	public List<Theme> getAllTheme() {
		
		return (List<Theme>) themeRepository.findAll();
	}
	
	
	/***
	 * Метод получения запретных/любимых тем пользователя
	 * 
	 * @param status - статус тем (-1 - запретные темы, 1 - любимые темы)
	 * 
	 * @return List<Theme>
	 */
	@Transactional
	public List<Theme> getUserLikesDislikeTheme(Integer status) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User user = userService.findByEmail(email).get();
		
		Collection<LikeDislikeTheme> likeDislikeTheme = likeDislikeThemeRepository.findByUser(user);
		
		List<Theme> themes =  new ArrayList<>();
		for(LikeDislikeTheme theme: likeDislikeTheme) {
			if(theme.getStatus().intValue() == status.intValue()) {
				themes.add(theme.getTheme());
			}
		}
		
		return themes;
	}
	
	
	/***
	 * Метод удаления статуса любимой/запретной темы
	 * 
	 * @param themeId - ID темы
	 * 
	 * @return InfoMessageResponse
	 */
	@Transactional
	public InfoMessageResponse deleteUserLikeDislikeTheme(Integer themeId) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User user = userService.findByEmail(email).get();
		
		try {
			Theme theme = themeRepository.findById(themeId).get();
			LikeDislikeTheme likeDislikeThemes = likeDislikeThemeRepository.findByUserAndTheme(user, theme).get();
			likeDislikeThemeRepository.delete(likeDislikeThemes);
			
			return new InfoMessageResponse(HttpStatus.OK.value(), "Тематика успешно убрана из понравившихся/запретных");
		} catch (Exception e) {
			return new InfoMessageResponse(HttpStatus.OK.value(), "Не удалось найти данную тематику");
		}
		
	} 
	
	
	/***
	 * Метод добавления запретных/любимых тем пользователя
	 * 
	 * @param themeId - ID темы
	 * @param status  - статус тем (-1 - запретные темы, 1 - любимые темы)
	 * 
	 * @return InfoMessageResponse
	 */
	public InfoMessageResponse addUserLikeDislikeTheme(Integer themeId, Integer status) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User user = userService.findByEmail(email).get();
		Theme theme = themeRepository.findById(themeId).get();
		
		try {
			LikeDislikeTheme likeDislikeThemes = likeDislikeThemeRepository.findByUserAndTheme(user, theme).get();
			
			likeDislikeThemeRepository.delete(likeDislikeThemes);
			likeDislikeThemes.setStatus(status);
			likeDislikeThemeRepository.save(likeDislikeThemes);
			
			return new InfoMessageResponse(HttpStatus.OK.value(), "Тема успешно добавлена в понравившиеся/запретные");
		} catch (NoSuchElementException e) {
			LikeDislikeTheme likeDislikeThemes = new LikeDislikeTheme();
			likeDislikeThemes.setStatus(status);
			likeDislikeThemes.setTheme(theme);
			likeDislikeThemes.setUser(user);
			likeDislikeThemes.setThemeId(theme.getId());
			likeDislikeThemes.setUserId(user.getId());
			
			likeDislikeThemeRepository.save(likeDislikeThemes);
			return new InfoMessageResponse(HttpStatus.OK.value(), "Тема успешно добавлена в понравившиеся/запретные");
		}
	}
	
	
	/***
	 * Метод проверки статуса темы, является ли она запретной/любимой
	 * 
	 * @param themeId - ID темы
	 * 
	 * @return LikeDislikeThemeResponse
	 */
	@Transactional
	public LikeDislikeThemeResponse checkUserLikeDislikeThemeStatus(Integer themeId) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User user = userService.findByEmail(email).get();
		Theme theme = themeRepository.findById(themeId).get();
		
		LikeDislikeThemeResponse dislikeThemeResponse = new LikeDislikeThemeResponse();
		
		try {
			LikeDislikeTheme likeDislikeThemes = likeDislikeThemeRepository.findByUserAndTheme(user, theme).get();
			
			dislikeThemeResponse.setStatus(likeDislikeThemes.getStatus());
			return dislikeThemeResponse;
		} catch (NoSuchElementException e) {
			dislikeThemeResponse.setStatus(0);
			return dislikeThemeResponse;
		}
		
	}
}
