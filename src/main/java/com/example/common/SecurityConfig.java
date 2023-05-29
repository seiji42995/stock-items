package com.example.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// SpringSecurityを導入すると毎回アクセス時にSpringがログイン画面を出してしまう
		// 下記の設定は全てのパスをログインしなくてもアクセスできるようにする設定
		http.authorizeHttpRequests()
				.requestMatchers("/css/**", "/js/**", "/login/**", "/login/login-check", "/register/**").permitAll()
				.requestMatchers("/item/**", "/add/**").hasRole("USER").anyRequest().authenticated();

		// ログインの処理
		http.formLogin(login -> login.loginProcessingUrl("/login").loginPage("/")
				.defaultSuccessUrl("/item/show-list", true).failureUrl("/?error=true").usernameParameter("mailAddress")
				.passwordParameter("password").permitAll());
		// ログアウト時の処理
		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout**")).logoutSuccessUrl("/")
				.deleteCookies("JSESSIONID").invalidateHttpSession(true);

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
