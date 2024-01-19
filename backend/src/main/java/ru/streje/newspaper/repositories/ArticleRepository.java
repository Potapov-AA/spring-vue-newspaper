package ru.streje.newspaper.repositories;

import org.springframework.data.repository.CrudRepository;

import ru.streje.newspaper.models.Article;

public interface ArticleRepository extends CrudRepository<Article, Integer> {
	Iterable<Article> findByOrderByIdDesc();
}
