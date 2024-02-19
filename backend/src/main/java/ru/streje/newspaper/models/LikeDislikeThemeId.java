package ru.streje.newspaper.models;

import java.io.Serializable;


import lombok.Data;

@Data
public class LikeDislikeThemeId implements Serializable {
	private Integer themeId;
	private Integer userId;	
}
