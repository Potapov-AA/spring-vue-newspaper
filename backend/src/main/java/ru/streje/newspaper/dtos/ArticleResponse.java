package ru.streje.newspaper.dtos;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ArticleResponse {
	private Integer id;
	private String title;
	private String text;
	private String image;
	private Date date;
	private List<String> themes;
	private Integer countLikes;
}
