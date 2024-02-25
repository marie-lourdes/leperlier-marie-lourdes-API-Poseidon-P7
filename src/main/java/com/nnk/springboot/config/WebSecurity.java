package com.nnk.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> {
			requests.requestMatchers("/css/**").permitAll();
			requests.requestMatchers("/").permitAll();	
			requests.requestMatchers("/user/add").permitAll();
			requests.requestMatchers("/user/validate").permitAll();			
			requests.requestMatchers("/admin/home").hasRole("ADMIN");
			requests.requestMatchers("/app/secure/article-details").hasRole("ADMIN");
			requests.requestMatchers("/user/list").hasRole("ADMIN");
			requests.requestMatchers("/ruleName/**").hasRole("ADMIN");
			requests.requestMatchers("/bidList/**").hasRole("USER");
			requests.requestMatchers("/curvePoint/**").hasRole("USER");
			requests.requestMatchers("/rating/**").hasRole("USER");	
			requests.requestMatchers("/trade/**").hasRole("USER");
			requests.requestMatchers("/user/{id}").hasRole("USER");
			requests.anyRequest().authenticated();

		})
	/*	.rememberMe((remember) -> {
			remember.rememberMeServices(rememberMeServices(authenticationService));
			remember.useSecureCookie(true);
		})*/
		.formLogin(form -> form.loginPage("/app/login").permitAll());
		/*.logout((logout) -> {
			logout.deleteCookies("JSESSIONID");
		});*/
		return http.build();
	}
}
