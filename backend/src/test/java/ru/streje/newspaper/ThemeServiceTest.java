package ru.streje.newspaper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.streje.newspaper.models.LikeDislikeTheme;
import ru.streje.newspaper.models.Theme;
import ru.streje.newspaper.models.User;
import ru.streje.newspaper.repositories.LikeDislikeThemeRepository;
import ru.streje.newspaper.repositories.ThemeRepository;
import ru.streje.newspaper.services.ThemeService;
import ru.streje.newspaper.services.UserService;
import ru.streje.newspaper.utilis.JwtTokenUtils;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ThemeServiceTest {
	
	@Autowired
	ThemeService themeService;
	
	@MockBean
	ThemeRepository themeRepository;
	
	@MockBean
	LikeDislikeThemeRepository likeDislikeThemeRepository;
	
	@MockBean
	JwtTokenUtils jwtTokenUtils;
	
	@MockBean
	UserService userService;
	
	
	@Test
	public void getAllTheme_returnResponseEntity_OK() {
		
		Collection<Theme> themes = new ArrayList<Theme>();
		themes.add(new Theme());
		themes.add(new Theme());
		
		doReturn(themes)
			.when(themeRepository)
			.findAll();
		 
		ResponseEntity<?> responseEntity = themeService.getAllTheme();
		
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	
	@Test
	public void getUserLikesDislikeTheme_returnResponseEntity_OK() {
		
		String mail = "mail@mail.ru";
		
		doReturn(mail)
			.when(jwtTokenUtils)
			.getUsername("token");
		
		User user = new User();
		
		doReturn(Optional.of(user))
			.when(userService)
			.findByEmail(mail);
		
		Collection<LikeDislikeTheme> likeDislikeThemes = new ArrayList<LikeDislikeTheme>();
		LikeDislikeTheme likeDislikeTheme = new LikeDislikeTheme();
		likeDislikeTheme.setStatus(1);
		likeDislikeTheme.setUser(user);
		likeDislikeThemes.add(likeDislikeTheme);
		
		doReturn(likeDislikeThemes)
			.when(likeDislikeThemeRepository)
			.findByUser(user);
		
		ResponseEntity<?> responseEntity = themeService.getUserLikesDislikeTheme("token", 1);
		
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	
	@Test
	public void deleteUserLikeDislikeTheme_returnResponseEntity_OK() {
		
		String mail = "mail@mail.ru";
		
		doReturn(mail)
			.when(jwtTokenUtils)
			.getUsername("token");
		
		User user = new User();
		
		doReturn(Optional.of(user))
			.when(userService)
			.findByEmail(mail);
		
		Theme theme = new Theme();
		
		doReturn(Optional.of(theme))
			.when(themeRepository)
			.findById(1);
		
		LikeDislikeTheme likeDislikeTheme = new LikeDislikeTheme();
		
		doReturn(Optional.of(likeDislikeTheme))
			.when(likeDislikeThemeRepository)
			.findByUserAndTheme(user, theme);
		
		ResponseEntity<?> responseEntity = themeService.deleteUserLikeDislikeTheme("token", 1);
		
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	
	@Test
	public void addUserLikeDislikeTheme_returnResponseEntity_OK() {
		
		String mail = "mail@mail.ru";
		
		doReturn(mail)
			.when(jwtTokenUtils)
			.getUsername("token");
		
		User user = new User();
		
		doReturn(Optional.of(user))
			.when(userService)
			.findByEmail(mail);
		
		Theme theme = new Theme();
		
		doReturn(Optional.of(theme))
			.when(themeRepository)
			.findById(1);
		
		LikeDislikeTheme likeDislikeTheme = new LikeDislikeTheme();
		
		doReturn(Optional.of(likeDislikeTheme))
			.when(likeDislikeThemeRepository)
			.findByUserAndTheme(user, theme);
		
		ResponseEntity<?> responseEntity = themeService.addUserLikeDislikeTheme("token", 1, 1);
		
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	
	@Test
	public void checkUserLikeDislikeThemeStatus_returnResponseEntity_OK() {
		
		String mail = "mail@mail.ru";
		
		doReturn(mail)
			.when(jwtTokenUtils)
			.getUsername("token");
		
		User user = new User();
		
		doReturn(Optional.of(user))
			.when(userService)
			.findByEmail(mail);
		
		Theme theme = new Theme();
		
		doReturn(Optional.of(theme))
			.when(themeRepository)
			.findById(1);
		
		LikeDislikeTheme likeDislikeTheme = new LikeDislikeTheme();
		
		doReturn(Optional.of(likeDislikeTheme))
			.when(likeDislikeThemeRepository)
			.findByUserAndTheme(user, theme);
		
		ResponseEntity<?> responseEntity = themeService.checkUserLikeDislikeThemeStatus("token", 1);
		
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}
