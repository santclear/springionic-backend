package com.santclear.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.santclear.security.UserSS;

public class UserService {
	
	public static UserSS authenticated() {
		try {
			// Retorna o usuário logado
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
}
