package ru.streje.newspaper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.streje.newspaper.repositories.ArticleRepository;
import ru.streje.newspaper.services.ArticleService;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {	
	
	@Mock
	ArticleRepository articleRepository;
	
	@InjectMocks
	ArticleService articleService;
	
	@Test
	@DisplayName("Получение списка всех статьей")
	void getAllArticles_returnValidResponseEntity() {
		var responseEntity = this.articleService.getAllArticle();
		
		System.out.println(responseEntity);
		
		assertNotNull(responseEntity);
	}
}
