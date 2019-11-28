package com.dbdr.controller;

import com.dbdr.domain.User;
import com.dbdr.response.ResponseService;
import com.dbdr.response.Result;
import com.dbdr.security.JwtTokenProvider;
import com.dbdr.security.exception.DuplicateUsersException;
import com.dbdr.security.exception.SigninFailedException;
import com.dbdr.security.exception.UserNotFoundException;
import com.dbdr.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final UserService userService;
	private final JwtTokenProvider jwtTokenProvider;
	private final ResponseService responseService;
	private final PasswordEncoder passwordEncoder;

	@PostMapping("/signin")
	public Result signin(String id, String password) {

		User user = userService.findByUserId(id);
		if (user == null) {
			throw new UserNotFoundException();
		} else if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new SigninFailedException();
		}
		log.info("LOGIN USER : " + user.getId());

		return responseService.getResult(jwtTokenProvider.createToken(user.getId(), user.getRoles()));
	}

	@PostMapping("/signup")
	public Result signup(User user) {

		if (userService.checkUserId(user.getId())) {
			throw new DuplicateUsersException();
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		return responseService.getSuccessResult();
	}

	@PutMapping("/user")
	public Result modify(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.modifyUser(user);

		return responseService.getSuccessResult();
	}
}
