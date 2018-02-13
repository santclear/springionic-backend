package com.santclear.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.santclear.security.JWTAuthenticationFilter;
import com.santclear.security.JWTUtil;

@Configuration
@EnableWebSecurity	
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
    private Environment env;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	// 1. Quais caminhos que por padrão estarão liberados. Nesse caso, a partir do "/h2-console/" todos os caminhos "**" estão liberados
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**"
	};

	private static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**",
			"/categorias/**",
			"/clientes/**"
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// Se o profile ativo em application.properties for o test, permite acesso ao BD H2
		// env.getActiveProfiles() obtém os profiles ativos
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }
		
		// cors() ativa o corsConfigurationSource()
		// csrf() desabilitado, pois o sistema é stateless, não mantém sessão
		http.cors().and().csrf().disable();
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()/* permite somente recuperar os dados (HttpMethod.GET), não podendo inserir, deletar, etc. */
			.antMatchers(PUBLIC_MATCHERS).permitAll() /* efetiva o item 1 */
			.anyRequest().authenticated();
		// Ativa o filtro criado em com.santclear.security.JWTAuthenticationFilter
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);/* assegura que o backend não armazenará sessão */
	}
	
	// Indica para o framework Spring Security quem é o UserDatailsService e quem realiza o encode da senha
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		// Permitindo acesso básico por multiplas fontes para todos os endpoints
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
