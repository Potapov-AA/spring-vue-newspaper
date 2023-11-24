package ru.streje.newspaper.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.dtos.RegistrationUserRequest;
import ru.streje.newspaper.models.User;
import ru.streje.newspaper.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
	private final UserRepository userRepository;
	private final RoleService roleService;
	private final PasswordEncoder passwordEncoder;
	
	/**
	 * Метод поиска пользователя по email
	 * @param email - почта пользователя
	 * @return экземпляр User
	 */
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = findByEmail(email).orElseThrow(
				() -> new UsernameNotFoundException(String.format("Пользователь с данной почтой '%s' не найден", email)));
		
		return new org.springframework.security.core.userdetails.User(
				user.getEmail(),
				user.getPassword(),
				user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
	}
	
	/**
	 * Метод создания нового пользователя
	 * @param registrationUserRequest - параметры для создания нового пользователя
	 */
	public void createNewUser(RegistrationUserRequest registrationUserRequest) {
		User user = new User();
		user.setEmail(registrationUserRequest.getEmail());
		user.setFirstname(registrationUserRequest.getFirstname());
		user.setLastname(registrationUserRequest.getLastname());
		user.setPassword(passwordEncoder.encode(registrationUserRequest.getPassword()));
		user.setRoles(List.of(roleService.getUserRole()));
		userRepository.save(user);
	}
	
	//ПО ИДЕЕ ПОКА ЧТО НЕ НУЖНЫ
	public Integer getUserId(String email) {
		User user = findByEmail(email).get();
		return user.getId();
	}
	
	public User getUser(String email) {
		return findByEmail(email).get();
	}
}