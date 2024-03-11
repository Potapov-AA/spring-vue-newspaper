package ru.streje.newspaper.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import ru.streje.newspaper.models.Comment;
import ru.streje.newspaper.repositories.CommentRepository;
import ru.streje.newspaper.services.ArticleService;
import ru.streje.newspaper.services.CommentService;
import ru.streje.newspaper.services.UserService;
import ru.streje.newspaper.utilis.JwtTokenUtils;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CommentServiceTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void getComments_returnResponseEntity_OK() {
		
	}
	
}
