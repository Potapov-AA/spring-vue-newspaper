package ru.streje.newspaper.services;

import org.springframework.http.ResponseEntity;

public interface LikeService {
	
	public ResponseEntity<?> addRemoveLike(String token, int articleId);
	
	public ResponseEntity<?> getUserLikeStatus(String token, int articleId);
	
	public ResponseEntity<?> getCountLikes(int articleId);
}
