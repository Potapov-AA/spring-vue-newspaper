package ru.streje.newspaper.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class InfoMessageResponse {
	private int status;
	private String message;
	private Date timestamp;
	
	public InfoMessageResponse(int status, String message) {
		this.status = status;
		this.message = message;
		this.timestamp = new Date();
	}
}
