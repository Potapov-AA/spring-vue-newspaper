package ru.streje.newspaper.services;

import ru.streje.newspaper.dtos.LikeResponse;

public interface LikeService {
	
	public LikeResponse addRemoveLike(String token, int articleId);
	
	public LikeResponse getUserLikeStatus(String token, int articleId);
	
	public LikeResponse getCountLikes(int articleId);
}
