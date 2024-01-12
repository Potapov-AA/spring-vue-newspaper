package ru.streje.newspaper.dtos;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ArticleResponse {
	Integer id;
	String title;
	String text;
	String image;
	Date date;
	List<String> themes;
	Integer countLikes;
}
