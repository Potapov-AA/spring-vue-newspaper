package ru.streje.newspaper.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.services.ThemeService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ThemeController {
	final private ThemeService themeService;
	
	@GetMapping("/allthemes")
	public ResponseEntity<?> getAllTheme() {
		return themeService.getAllTheme();
	}
	
	@GetMapping("/likethemes")
	public ResponseEntity<?> getUserLikesTheme(@RequestHeader("authorization") String token) {
		
		return themeService.getUserLikesDislikeTheme(token.substring(7), 1);
	}
	
	@GetMapping("/dislikethemes")
	public ResponseEntity<?> getUserDislikesTheme(@RequestHeader("authorization") String token) {

		return themeService.getUserLikesDislikeTheme(token.substring(7), -1);
	}
	
	@PostMapping("/addliketheme/{id}")
	public ResponseEntity<?> addUserLikeTheme(@RequestHeader("authorization") String token, 
			@PathVariable("id") Integer id) {
		
		return themeService.addUserLikeDislikeTheme(token.substring(7), id, 1);
	}
	
	@PostMapping("/adddisliketheme/{id}")
	public ResponseEntity<?> addUserDislikeTheme(@RequestHeader("authorization") String token, 
			@PathVariable("id") Integer id) {
		
		return themeService.addUserLikeDislikeTheme(token.substring(7), id, -1);
	}
	
	@DeleteMapping("/deletelikedislikethemes/{id}")
	public ResponseEntity<?> deleteUserLikeDislikeTheme(@RequestHeader("authorization") String token, 
			@PathVariable("id") Integer id) {
		
		return themeService.deleteUserLikeDislikeTheme(token.substring(7), id);
	}
	
	@GetMapping("/checkstatus/{id}")
	public ResponseEntity<?> checkUserLikeDislikeThemeStatus(@RequestHeader("authorization") String token, 
			@PathVariable("id") Integer id) {
		
		return themeService.checkUserLikeDislikeThemeStatus(token.substring(7), id);
	}
}
