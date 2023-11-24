package ru.streje.newspaper.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.models.Role;
import ru.streje.newspaper.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
	private final RoleRepository roleRepository;
	
	/**
	 * Получение экземпляр класса Role с названием USER
	 * @return экземпляр класса Role
	 */
	public Role getUserRole() {
		return roleRepository.findByName("ROLE_USER").get();
	}
}