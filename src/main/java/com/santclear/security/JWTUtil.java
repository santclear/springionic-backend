package com.santclear.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	// O valor jwt.secret definido no application.properties é atribuido ao atributo secret
	@Value("${jwt.secret}")
	private String secret;

	// O valor jwt.expiration definido no application.properties é atribuido ao atributo secret
	@Value("${jwt.expiration}")
	private Long expiration;
	
	// Gerador de token
	public String generateToken(String username) {
		// builder gera o token, setSubject define o usuário do token, 
		// setExpiration define o tempo de expiração do token,
		// signWith qual é tipo do algoritmo que irá assinar o token
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
}
