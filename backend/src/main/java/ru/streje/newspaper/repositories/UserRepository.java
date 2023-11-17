package ru.streje.newspaper.repositories;

import org.springframework.data.repository.CrudRepository;

import ru.streje.newspaper.models.User;

public interface UserRepository extends CrudRepository<User, Integer>{

}
