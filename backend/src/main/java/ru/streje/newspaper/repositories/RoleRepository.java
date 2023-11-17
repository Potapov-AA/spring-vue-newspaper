package ru.streje.newspaper.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ru.streje.newspaper.models.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
	Optional<Role> findByName(String name);
}
