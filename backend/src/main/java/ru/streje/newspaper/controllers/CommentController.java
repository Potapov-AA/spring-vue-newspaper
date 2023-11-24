package ru.streje.newspaper.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.dtos.CommentRequest;
import ru.streje.newspaper.services.CommentService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class CommentController {
	private final CommentService commentService;
	
	@GetMapping("/comments/{articleId}")
	public ResponseEntity<?> getComments(@RequestHeader("authorization") String token, @PathVariable("articleId") Integer articleId) {
		return null;
	}
	
	@PostMapping("/addcomment/{articleId")
	public ResponseEntity<?> postComment(@RequestHeader("authorization") String token, @PathVariable("articleId") Integer articleId, CommentRequest commentRequest) {
		return null;
	}
	
}
