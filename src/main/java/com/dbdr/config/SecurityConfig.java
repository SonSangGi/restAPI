package com.dbdr.config;

import com.dbdr.security.CustomAccessDenieHadler;
import com.dbdr.security.CustomAuthenticationEntryPoint;
import com.dbdr.security.JwtAuthenticationFilter;
import com.dbdr.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * securiry 보안 설정
 *
 * 커스텀 예외처리 미적용 이유
 * ControllerAdvice는 Spring이 처리 가능한 영역까지 리퀘스트가 도달해야 처리할 수 있음
 * Spring Security는 Spring 앞단에서 필터링하기 때문에 exception이 Spring의 서블릿까지 도달하지 않음
 */
@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtTokenProvider jwtTokenProvider;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.httpBasic().disable() // rest api이므로 기본설정 사용 안. 기본 설정은 비인증 시 로그인폼 화면으로 리다이렉트 됨 ***
				.csrf().disable() // rest api 이므로 csrf 보안이 필요 없음
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //jwt token으로 인증하므로 세션은 필요없으므로 생성안함
				.and()
				.authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
				.antMatchers("/test","/exception/**").permitAll()
				.antMatchers(HttpMethod.POST,"/signin","/signup").permitAll() // 전체 권한
				.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().hasRole("USER") // 유저 권한
				.and()
				.exceptionHandling().accessDeniedHandler(new CustomAccessDenieHadler()).and()
				.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()).and()
				.formLogin().disable()
				.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // jwt token 필터를 id/password 인증 필터 전에 넣는다
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	// web설정하는게 http설정보다 우선순위
	/*
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/admin");
	}
	*/
}
