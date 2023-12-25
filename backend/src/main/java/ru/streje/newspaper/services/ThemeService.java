package ru.streje.newspaper.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.models.Theme;
import ru.streje.newspaper.repositories.ThemeRepository;

@Service
@RequiredArgsConstructor
public class ThemeService {
	private final ThemeRepository themeRepository;

	/**
	 * Метод получения экземпляра Theme по переданому имени, если темы с данным
	 * именем нет, то возвращает null
	 * 
	 * @param name - название темы
	 * @return экземпляр Theme / null
	 */
	public Theme getTheme(String name) {
		try {
			Theme theme = themeRepository.findByName(name).get();
			return theme;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Метод добавления новой темы
	 * 
	 * @param name - название темы
	 */
	public void addTheme(String name) {
		Theme theme = new Theme();
		theme.setName(name);

		themeRepository.save(theme);
	}
}
