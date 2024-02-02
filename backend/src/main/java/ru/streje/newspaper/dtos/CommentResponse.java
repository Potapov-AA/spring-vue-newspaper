package ru.streje.newspaper.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class CommentResponse {
	Integer id;
	String firstName;
	String lastName;
	String text;
	Date date;
}
