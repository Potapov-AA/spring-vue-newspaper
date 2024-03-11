package ru.streje.newspaper.services;

import org.springframework.http.ResponseEntity;

import ru.streje.newspaper.dtos.CommentRequest;

public interface CommentService {
	
	public ResponseEntity<?> getComments(int articleId);
	
	public ResponseEntity<?> addComment(String token, int articleId, CommentRequest commentRequest);
	
	public ResponseEntity<?> deleteComment(int commentId);
}
