package ru.streje.newspaper.models;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "article")
@Data
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "title")
	private String title;

	@Column(name = "text")
	private String text;

	@Column(name = "image")
	private String image;

	@Column(name = "date")
	private Date date;

	@ManyToMany
	@JoinTable(name = "theme_article", 
		joinColumns = @JoinColumn(name = "article_id"), 
		inverseJoinColumns = @JoinColumn(name = "theme_id"))
	private Collection<Theme> themes;

	@ManyToMany
	@JoinTable(name = "likes", 
		joinColumns = @JoinColumn(name = "article_id"), 
		inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Collection<User> users;
}
