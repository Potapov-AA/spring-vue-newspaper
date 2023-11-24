package ru.streje.newspaper.dtos;

import java.sql.Date;

import lombok.Data;

@Data
public class ArticleRequest {
	private String title;
	private String text;
	private byte[] image;
}
