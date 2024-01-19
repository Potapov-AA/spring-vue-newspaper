package ru.streje.newspaper.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;

import ru.streje.newspaper.models.LikeDislikeTheme;
import ru.streje.newspaper.models.Theme;
import ru.streje.newspaper.models.User;

public interface LikeDislikeThemeRepository extends CrudRepository<LikeDislikeTheme, IdKey> {
	Collection<LikeDislikeTheme> findByUser(User user);
	Optional<LikeDislikeTheme> findByUserAndTheme(User user, Theme theme);
}
