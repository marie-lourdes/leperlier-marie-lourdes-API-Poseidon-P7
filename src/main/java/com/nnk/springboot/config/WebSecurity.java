package com.nnk.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
			requests.requestMatchers("/admin/home").hasRole("ADMIN");
			requests.requestMatchers("/app/secure/article-details").hasRole("ADMIN");
			requests.requestMatchers("/user/**").hasRole("ADMIN");
			requests.requestMatchers("/ruleName/**").hasRole("ADMIN");
			requests.requestMatchers("/bidList/**").hasRole("ADMIN");
			requests.requestMatchers("/curvePoint/**").hasRole("ADMIN");
			requests.requestMatchers("/rating/**").hasRole("ADMIN");	
			requests.requestMatchers("/trade/**").hasRole("ADMIN");
			requests.requestMatchers(HttpMethod.GET,"/bidList/list").hasRole("USER");
			requests.requestMatchers(HttpMethod.GET,"/curvePoint/**").hasRole("USER");
			requests.requestMatchers(HttpMethod.GET,"/rating/**").hasRole("USER");	
			requests.requestMatchers(HttpMethod.GET,"/trade/**").hasRole("USER");
			requests.anyRequest().authenticated();

		})	/*.rememberMe((remember) -> {
			remember.rememberMeServices(rememberMeServices(authenticationService));
			remember.useSecureCookie(true);
		})*/
		.formLogin(form -> form.loginPage("/app/login").permitAll())
		.logout((logout) -> {
			logout.deleteCookies("JSESSIONID");
			logout.permitAll();
		});
		return http.build();
	}
}
