package ru.streje.newspaper.messages;

import java.util.Date;

import lombok.Data;

@Data
public class ErrorMessage {
	private int status;
	private String message;
	private Date timestamp;

	public ErrorMessage(int status, String message) {
		this.status = status;
		this.message = message;
		this.timestamp = new Date();
	}
}