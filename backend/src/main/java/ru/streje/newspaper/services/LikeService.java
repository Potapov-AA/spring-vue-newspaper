package ru.streje.newspaper.services;

import ru.streje.newspaper.dtos.LikeResponse;

public interface LikeService {
	
	public LikeResponse addRemoveLike(int articleId);
	
	public LikeResponse getUserLikeStatus(int articleId);
	
	public LikeResponse getCountLikes(int articleId);
}
