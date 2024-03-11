package ru.streje.newspaper.services;

import org.springframework.http.ResponseEntity;

import ru.streje.newspaper.models.Theme;


public interface ThemeService {
	
	public Theme getTheme(String name);
	
	public void addTheme(String name);
	
	public ResponseEntity<?> getAllTheme();
	
	public ResponseEntity<?> getUserLikesDislikeTheme(String token, Integer status);
	
	public ResponseEntity<?> deleteUserLikeDislikeTheme(String token, Integer themeId);
	
	public ResponseEntity<?> addUserLikeDislikeTheme(String token, Integer themeId, Integer status);
	
	public ResponseEntity<?> checkUserLikeDislikeThemeStatus(String token, Integer themeId);
}
