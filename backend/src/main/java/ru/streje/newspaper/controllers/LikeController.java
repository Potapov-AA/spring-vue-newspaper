package ru.streje.newspaper.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.streje.newspaper.dtos.LikeResponse;
import ru.streje.newspaper.services.LikeService;

@CrossOrigin
@RestController
public class LikeController {
	
	@Autowired
	private LikeService likeService;

	
	@GetMapping("/likestatus/{articleId}")
	public LikeResponse getLikeStatus(@PathVariable("articleId") Integer articleId) {

		return likeService.getUserLikeStatus(articleId);
	}

	
	@GetMapping("/countlikes/{articleId}")
	public LikeResponse getCountLikes(@PathVariable("articleId") Integer articleId) {
		
		return likeService.getCountLikes(articleId);
	}

	
	@PostMapping("/addremovelike/{articleId}")
	public LikeResponse postLike(@PathVariable("articleId") Integer articleId) {

		return likeService.addRemoveLike(articleId);
	}
}
