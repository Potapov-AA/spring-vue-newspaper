package ru.streje.newspaper.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "comment")
@Data
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "text")
	private String text;

	@Column(name = "date")
	private Date date;

	@ManyToOne
	private Article article;

	@ManyToOne
	private User user;
}
