package ru.streje.newspaper.repositories;

import org.springframework.data.repository.CrudRepository;

import ru.streje.newspaper.models.Comment;

public interface CommentRepository extends CrudRepository<Comment, Integer>{
	
}
