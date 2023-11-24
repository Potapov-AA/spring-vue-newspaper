package ru.streje.newspaper.dtos;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class ArticlesResponse {
	Integer id;
	String title;
	String text;
	byte[] image;
	Date date;
	List<String> themes;
	Integer countLikes;
}
