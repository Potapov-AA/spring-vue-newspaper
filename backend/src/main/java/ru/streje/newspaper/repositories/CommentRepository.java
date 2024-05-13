package ru.streje.newspaper.repositories;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import ru.streje.newspaper.models.Comment;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
	Collection<Comment> findByArticleId(int articleId);
}
