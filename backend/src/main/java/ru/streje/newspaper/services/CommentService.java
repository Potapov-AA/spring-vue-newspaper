package ru.streje.newspaper.services;

import java.util.List;

import ru.streje.newspaper.dtos.CommentRequest;
import ru.streje.newspaper.dtos.CommentResponse;
import ru.streje.newspaper.dtos.InfoMessageResponse;

public interface CommentService {
	
	public List<CommentResponse> getComments(int articleId);
	
	public InfoMessageResponse addComment(String token, int articleId, CommentRequest commentRequest);
	
	public InfoMessageResponse deleteComment(int commentId);
}
