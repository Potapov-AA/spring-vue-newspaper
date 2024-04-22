package ru.streje.newspaper.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import ru.streje.newspaper.dtos.InfoMessage;
import ru.streje.newspaper.dtos.LikeDislikeThemeResponse;
import ru.streje.newspaper.models.Theme;


public interface ThemeService {
	
	public Theme getTheme(String name);
	
	public void addTheme(String name);
	
	public List<Theme> getAllTheme();
	
	public List<Theme> getUserLikesDislikeTheme(String token, Integer status);
	
	public InfoMessage deleteUserLikeDislikeTheme(String token, Integer themeId);
	
	public InfoMessage addUserLikeDislikeTheme(String token, Integer themeId, Integer status);
	
	public LikeDislikeThemeResponse checkUserLikeDislikeThemeStatus(String token, Integer themeId);
}
