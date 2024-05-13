package ru.streje.newspaper.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class CommentResponse {
	private Integer id;
	private String firstName;
	private String lastName;
	private String text;
	private Date date;
}
