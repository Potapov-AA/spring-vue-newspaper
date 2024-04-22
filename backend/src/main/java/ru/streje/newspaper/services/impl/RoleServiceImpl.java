package ru.streje.newspaper.services.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.models.Role;
import ru.streje.newspaper.repositories.RoleRepository;
import ru.streje.newspaper.services.RoleService;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
	
	private final RoleRepository roleRepository;

	/**
	 * Получение экземпляр класса Role с типом USER
	 * 
	 * @return Role
	 */
	public Role getUserRole() {
		
		return roleRepository.findByName("ROLE_USER").get();
	}
}