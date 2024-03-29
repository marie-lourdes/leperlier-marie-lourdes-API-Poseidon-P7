package com.nnk.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
/**
 * Class configuration for add filter  chain with acces control and add form login
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurity {

	/**
	 * filter chain used to secure acces control of request method according to role
	 * of user and configure form login page
	 *
	 * @return SecurityFilterChain
	 */
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> {
			requests.requestMatchers("/css/**").permitAll();
			requests.requestMatchers("/").permitAll();
			requests.requestMatchers("/user/add").permitAll();
			requests.requestMatchers("/user/validate").permitAll();
			requests.requestMatchers("/user/list").hasRole("ADMIN");
			requests.requestMatchers("/admin/home").hasRole("ADMIN");
			requests.requestMatchers("/app/secure/article-details").hasRole("ADMIN");
			requests.requestMatchers("/ruleName/**").hasRole("ADMIN");
			requests.requestMatchers("/*/add").hasRole("ADMIN");
			requests.requestMatchers("/*/validate").hasRole("ADMIN");
			requests.requestMatchers("/*/update/**").hasRole("ADMIN");
			requests.requestMatchers("/*/delete/**").hasRole("ADMIN");
			requests.anyRequest().authenticated();
		}).formLogin(form -> form.loginPage("/app/login").permitAll()).logout((logout) -> {
			logout.deleteCookies("JSESSIONID");
			logout.permitAll();
		});
		return http.build();
	}
}
