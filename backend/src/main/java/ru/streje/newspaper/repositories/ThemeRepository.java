package ru.streje.newspaper.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ru.streje.newspaper.models.Theme;

public interface ThemeRepository extends CrudRepository<Theme, Integer>{
	Optional<Theme> findByName(String name);
} 
