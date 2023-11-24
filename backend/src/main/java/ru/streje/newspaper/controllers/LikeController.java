package ru.streje.newspaper.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.services.LikeService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class LikeController {
	final private LikeService likeService;
	
	@GetMapping("/likestatus/{articleId}")
	public ResponseEntity<?> getLikestatus() {
		return null;
	}
	
	@PostMapping("/addremovelike/{articleId}")
	public ResponseEntity<?> postLike(@RequestHeader("authorization") String token, @PathVariable("articleId") Integer articleId) {
		return likeService.addRemoveLike(token.substring(7), articleId);
	}
}
