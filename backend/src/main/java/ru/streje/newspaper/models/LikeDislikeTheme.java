package ru.streje.newspaper.models;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@IdClass(LikeDislikeTheme.class)
@Table(name = "user_theme_like_dislike")
@Data
public class LikeDislikeTheme implements Serializable {
	@Column(name = "status")
	private Integer status;
	
	@Id
	@ManyToOne
	private Theme theme;
	
	@Id
	@ManyToOne
	private User user;
}
