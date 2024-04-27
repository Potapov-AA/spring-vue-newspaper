package ru.streje.newspaper.services;

import java.util.List;

import ru.streje.newspaper.dtos.InfoMessageResponse;
import ru.streje.newspaper.dtos.LikeDislikeThemeResponse;
import ru.streje.newspaper.models.Theme;


public interface ThemeService {
	
	public Theme getTheme(String name);
	
	public void addTheme(String name);
	
	public List<Theme> getAllTheme();
	
	public List<Theme> getUserLikesDislikeTheme(Integer status);
	
	public InfoMessageResponse deleteUserLikeDislikeTheme(Integer themeId);
	
	public InfoMessageResponse addUserLikeDislikeTheme(Integer themeId, Integer status);
	
	public LikeDislikeThemeResponse checkUserLikeDislikeThemeStatus(Integer themeId);
}
