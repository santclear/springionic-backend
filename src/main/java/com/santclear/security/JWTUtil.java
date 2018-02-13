package com.santclear.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//FIXME JWT, passo 6
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
	
	public boolean tokenValido(String token) {
		// Objeto que armazena as reivindicações definidas no token.
		// Nota: o usuário que está acessando por meio de tokens, 
		// reivindica o acesso alegando que é um determinado usuário e que tem um tempo de expiração determinado
		Claims claims = getClaims(token);
		
		if (claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			// Valida se o token, nesse caso, se o usuário for diferente de nulo, 
			// a data de expiração for diferente de nulo e a data atual não ultrapassou 
			// o tempo final de expiração, então é válido
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		
		return false;
	}
	
	public String getUsername(String token) {
		Claims claims = getClaims(token);
		
		if (claims != null) {
			return claims.getSubject();
		}
		
		return null;
	}
	
	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}
}
