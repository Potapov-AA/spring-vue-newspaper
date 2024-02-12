package ru.streje.newspaper.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.services.UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private UserService userService;
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
	
	@Autowired
	public void setJwtRequestFilter(JwtRequestFilter jwtRequestFilter) {
		this.jwtRequestFilter = jwtRequestFilter;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors().and().csrf().disable()
			.authorizeRequests()	
			.antMatchers("/articles").permitAll() 
			.antMatchers("/comments/{articleId}").permitAll()
			.antMatchers("/countlikes/{articleId}").permitAll()
			.antMatchers("/addcomment/{articleId}").authenticated()
			.antMatchers("/addremovelike/{articleId}").authenticated()
			.antMatchers("/likestatus/{articleId}").authenticated()	
			.antMatchers("/likethemes").authenticated() 
			.antMatchers("/allthemes").authenticated() 
			.antMatchers("/dislikethemes").authenticated()
			.antMatchers("/addliketheme/{id}").authenticated()
			.antMatchers("/adddisliketheme/{id}").authenticated()
			.antMatchers("/deletelikedislikethemes/{id}").authenticated()
			.antMatchers("/checkstatus/{id}").authenticated()
			.antMatchers("/checktoken").authenticated()
			.antMatchers("/newarticle").hasRole("ADMIN")
			.antMatchers("/article/{id}").hasRole("ADMIN")
			.antMatchers("/deletearticle/{id}").hasRole("ADMIN")
			.antMatchers("/changearticle/{id}").hasRole("ADMIN")
			.antMatchers("/deletecomment/{commentId}").hasRole("ADMIN")
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
			.and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(userService);
		
		return daoAuthenticationProvider;
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
