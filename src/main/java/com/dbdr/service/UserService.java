package com.dbdr.service;

import com.dbdr.domain.User;
import com.dbdr.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {
		return userMapper.findByUserId(userPk);
	}

	public User findByUserId(String id){
		return userMapper.findByUserId(id);
	}

	public boolean checkUserId(String id) {
		return userMapper.checkUserId(id);
	}

	public void modifyUser(User user) {
		userMapper.modifyUser(user);
	}
}
