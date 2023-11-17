package ru.streje.newspaper.messages;

import lombok.Data;

@Data
public class SuccesMessage {
	private String message;

	public SuccesMessage(String message) {
		this.message = message;
	}
}