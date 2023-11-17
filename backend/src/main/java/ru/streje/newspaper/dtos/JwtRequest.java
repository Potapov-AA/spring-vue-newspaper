package ru.streje.newspaper.dtos;

import lombok.Data;

@Data
public class JwtRequest {
	private String email;
	private String password;
}
