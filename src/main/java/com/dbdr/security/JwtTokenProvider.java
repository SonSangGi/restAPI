package com.dbdr.security;

import com.dbdr.domain.User;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Jwt 토큰 생성 및 유효성 검증
 * https://daddyprogrammer.org/post/636/springboot2-springsecurity-authentication-authorization/
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

	@Value("spring.jwt.secret")
	private String secretKey;

	private long tokenValidMilisecond = 1000L * 60 * 60; // 토큰 유효 시간 (한시간)

	private final UserDetailsService userDetailsService;

	@PostConstruct //WAS 실행 시 초기화 ***
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	//Jwt 토큰 생성
	public String createToken(String userPk, List<String> roles) {
		Claims claims = Jwts.claims().setSubject(userPk);
		claims.put("roles", roles);

		Date now = new Date();
		return Jwts.builder()
				.setClaims(claims)  // 데이터
				.setIssuedAt(now) // 토큰 발행 일자
				.setExpiration(new Date(now.getTime() + tokenValidMilisecond)) // 토큰 유효 시간
				.signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
				.compact();
	}

	//JWT 토큰으로 인증 정보를 조회
	public Authentication getAututhentication(String token) {
		//UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));

		Map<String, Object> parseInfo = getUserParseInfo(token);
		User user = new User();
		user.setId((String) parseInfo.get("id"));
		user.setRoles((List<String>) parseInfo.get("authorities"));
		return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	}

	//Jwt 토큰에서 회원 구별 정보 추출
	public Map<String, Object> getUserParseInfo(String token) {
		Jws<Claims> paseInfo = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
		Map<String, Object> result = new HashMap<>();
		result.put("id", paseInfo.getBody().getSubject());
		result.put("authorities", paseInfo.getBody().get("roles", List.class));
		return result;
	}

	// Request의 Header에서 token 파싱 : "X-AUTH-TOKEN: jwt토큰"
	public String resolveToken(HttpServletRequest req) {
		return req.getHeader("X-AUTH-TOKEN");
	}

	// Jwt 토큰의 유효성 + 만료일자 확인
	public boolean validateToken(String jwtToken) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (ExpiredJwtException e) {
			log.info("토큰 만료");
			return false;
		} catch (JwtException e) {
			log.info("토큰 변조");
			return false;
		}
	}
}
