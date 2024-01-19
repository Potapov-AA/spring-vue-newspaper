package ru.streje.newspaper.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@IdClass(LikeDislikeTheme.class)
@Table(name = "user_theme_like_dislike")
@Data
public class LikeDislikeTheme {
	@Column(name = "status")
	private Integer status;
	
	@Id
	@ManyToOne
	private Theme theme;
	
	@Id
	@ManyToOne
	private User user;
}
