package ru.streje.newspaper.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.streje.newspaper.models.Role;
import ru.streje.newspaper.repositories.RoleRepository;
import ru.streje.newspaper.services.RoleService;


@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;

	
	/**
	 * Получение экземпляр класса Role с типом USER
	 * 
	 * @return Role
	 */
	public Role getUserRole() {
		
		return roleRepository.findByName("ROLE_USER").get();
	}
}