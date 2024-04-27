package ru.streje.newspaper.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.dtos.CommentRequest;
import ru.streje.newspaper.dtos.CommentResponse;
import ru.streje.newspaper.dtos.InfoMessageResponse;
import ru.streje.newspaper.services.CommentService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class CommentController {
	
	private final CommentService commentService;

	
	@GetMapping("/comments/{articleId}")
	public List<CommentResponse> getComments(@PathVariable("articleId") Integer articleId) {
		
		return commentService.getComments(articleId);
	}

	
	@PostMapping("/addcomment/{articleId}")
	public InfoMessageResponse postComment(
			@PathVariable("articleId") Integer articleId, 
			@RequestBody CommentRequest commentRequest) {
		
		return commentService.addComment(articleId, commentRequest);
	}
	
	
	@DeleteMapping("/deletecomment/{commentId}")
	public InfoMessageResponse deleteComment(@PathVariable("commentId") Integer commentId) {
		
		return commentService.deleteComment(commentId);
	}
}
