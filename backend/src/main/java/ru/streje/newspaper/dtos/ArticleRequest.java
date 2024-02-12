package ru.streje.newspaper.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class ArticleRequest {
	@Size
	@Size(min = 5, max = 255, message = "Название статьи должно состоять минимум из 5 и не более чем из 255 символов")
	private String title;

	@Size(min = 20, message = "Текст статьи должен быть больше 20 символов")
	private String text;
	
	@NotNull(message = "Добавьте изображение")
	private String image;
	
	@Size(min = 1, message = "Статья должна содержать минимум одну тему")
	private String[] themes;
}
