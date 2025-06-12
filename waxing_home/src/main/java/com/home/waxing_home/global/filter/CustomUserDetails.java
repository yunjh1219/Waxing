package com.home.waxing_home.global.filter;

import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

	private String username;

	private Collection<? extends GrantedAuthority> authorities;

	@Builder
	private CustomUserDetails(String username, Collection<? extends GrantedAuthority> authorities) {
		this.username = username;

		this.authorities = authorities;
	}

	public static CustomUserDetails of(String username, Collection<? extends GrantedAuthority> authorities) {
		return CustomUserDetails.builder()
			.username(username)
			.authorities(authorities)
			.build();
	}

	public static CustomUserDetails of(Claims claims) {
		return CustomUserDetails.builder()
			.username(claims.getSubject())
			.authorities(convertAuthorities(claims.get("role", String.class)))
			.build();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return username;
	}

	private static List<? extends GrantedAuthority> convertAuthorities(String... roles) {
		return Arrays.stream(roles)
			.map(role -> new SimpleGrantedAuthority("ROLE_" + role))
			.toList();
	}

}
