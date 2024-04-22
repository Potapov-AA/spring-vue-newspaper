package ru.streje.newspaper.services;

import java.util.List;

import ru.streje.newspaper.dtos.CommentRequest;
import ru.streje.newspaper.dtos.CommentResponse;
import ru.streje.newspaper.dtos.InfoMessage;

public interface CommentService {
	
	public List<CommentResponse> getComments(int articleId);
	
	public InfoMessage addComment(String token, int articleId, CommentRequest commentRequest);
	
	public InfoMessage deleteComment(int commentId);
}
