package ru.streje.newspaper.repositories;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import ru.streje.newspaper.models.Article;

public interface ArticleRepository extends CrudRepository<Article, Integer> {
	Collection<Article> findByOrderByIdDesc();
}
