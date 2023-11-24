package ru.streje.newspaper.dtos;

import java.sql.Date;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ArticleRequest {
	@Size(min = 5, max = 255, message = "Название статьи должно состоять минимум из 5 и не более чем из 255 символов")
	private String title;
	
	@Size(min = 20, message = "Текст статьи должен быть больше 20 символов")
	private String text;
	
	private byte[] image;
}
