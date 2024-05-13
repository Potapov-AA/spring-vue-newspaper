package ru.streje.newspaper.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.streje.newspaper.dtos.InfoMessageResponse;
import ru.streje.newspaper.dtos.LikeDislikeThemeResponse;
import ru.streje.newspaper.models.Theme;
import ru.streje.newspaper.services.ThemeService;

@CrossOrigin
@RestController
public class ThemeController {
	
	@Autowired
	private ThemeService themeService;
	
	
	@GetMapping("/allthemes")
	public List<Theme> getAllTheme() {
		
		return themeService.getAllTheme();
	}
	
	
	@GetMapping("/likethemes")
	public List<Theme> getUserLikesTheme() {
		
		return themeService.getUserLikesDislikeTheme(1);
	}
	
	
	@GetMapping("/dislikethemes")
	public List<Theme> getUserDislikesTheme() {

		return themeService.getUserLikesDislikeTheme(-1);
	}
	
	
	@PostMapping("/addliketheme/{id}")
	public InfoMessageResponse addUserLikeTheme(@PathVariable("id") Integer id) {
		
		return themeService.addUserLikeDislikeTheme(id, 1);
	}
	
	
	@PostMapping("/adddisliketheme/{id}")
	public InfoMessageResponse addUserDislikeTheme(@PathVariable("id") Integer id) {
		
		return themeService.addUserLikeDislikeTheme(id, -1);
	}
	
	
	@DeleteMapping("/deletelikedislikethemes/{id}")
	public InfoMessageResponse deleteUserLikeDislikeTheme(@PathVariable("id") Integer id) {
		
		return themeService.deleteUserLikeDislikeTheme(id);
	}
	
	
	@GetMapping("/checkstatus/{id}")
	public LikeDislikeThemeResponse checkUserLikeDislikeThemeStatus(@PathVariable("id") Integer id) {
		
		return themeService.checkUserLikeDislikeThemeStatus(id);
	}
}
