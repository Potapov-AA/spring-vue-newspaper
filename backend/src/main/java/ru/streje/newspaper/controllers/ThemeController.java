package ru.streje.newspaper.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.dtos.InfoMessage;
import ru.streje.newspaper.dtos.LikeDislikeThemeResponse;
import ru.streje.newspaper.models.Theme;
import ru.streje.newspaper.services.ThemeService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ThemeController {
	final private ThemeService themeService;
	
	@GetMapping("/allthemes")
	public List<Theme> getAllTheme() {
		
		return themeService.getAllTheme();
	}
	
	@GetMapping("/likethemes")
	public List<Theme> getUserLikesTheme(@RequestHeader("authorization") String token) {
		
		return themeService.getUserLikesDislikeTheme(token.substring(7), 1);
	}
	
	@GetMapping("/dislikethemes")
	public List<Theme> getUserDislikesTheme(@RequestHeader("authorization") String token) {

		return themeService.getUserLikesDislikeTheme(token.substring(7), -1);
	}
	
	@PostMapping("/addliketheme/{id}")
	public InfoMessage addUserLikeTheme(
			@RequestHeader("authorization") String token, 
			@PathVariable("id") Integer id) {
		
		return themeService.addUserLikeDislikeTheme(token.substring(7), id, 1);
	}
	
	@PostMapping("/adddisliketheme/{id}")
	public InfoMessage addUserDislikeTheme(
			@RequestHeader("authorization") String token, 
			@PathVariable("id") Integer id) {
		
		return themeService.addUserLikeDislikeTheme(token.substring(7), id, -1);
	}
	
	@DeleteMapping("/deletelikedislikethemes/{id}")
	public InfoMessage deleteUserLikeDislikeTheme(
			@RequestHeader("authorization") String token, 
			@PathVariable("id") Integer id) {
		
		return themeService.deleteUserLikeDislikeTheme(token.substring(7), id);
	}
	
	@GetMapping("/checkstatus/{id}")
	public LikeDislikeThemeResponse checkUserLikeDislikeThemeStatus(
			@RequestHeader("authorization") String token, 
			@PathVariable("id") Integer id) {
		
		return themeService.checkUserLikeDislikeThemeStatus(token.substring(7), id);
	}
}
