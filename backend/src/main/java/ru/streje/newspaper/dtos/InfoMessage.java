package ru.streje.newspaper.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class InfoMessage {
	private int status;
	private String message;
	private Date timestamp;
	
	public InfoMessage(int status, String message) {
		this.status = status;
		this.message = message;
		this.timestamp = new Date();
	}
}
