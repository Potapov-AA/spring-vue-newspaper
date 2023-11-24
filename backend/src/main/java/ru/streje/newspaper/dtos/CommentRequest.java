package ru.streje.newspaper.dtos;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequest {
	@Size(min = 1, max = 1000, message = "Комментарий должен быть меньше 1000 символов")
	private String text;
}
