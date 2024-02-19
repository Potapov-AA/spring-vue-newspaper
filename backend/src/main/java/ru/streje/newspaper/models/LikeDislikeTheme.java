package ru.streje.newspaper.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user_theme_like_dislike")
@IdClass(LikeDislikeThemeId.class)
@Data
public class LikeDislikeTheme{
	@Column(name = "status")
	private Integer status;
	
	@Id
	@Column(name = "theme_id")
	private Integer themeId;
	
	@Id
	@Column(name = "user_id")
	private Integer userId;
	
	
	@ManyToOne
	@JoinColumn(name = "theme_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Theme theme;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;
}
