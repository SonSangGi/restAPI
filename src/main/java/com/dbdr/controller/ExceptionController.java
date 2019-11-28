package com.dbdr.controller;

import com.dbdr.response.Result;
import com.dbdr.security.exception.AccessDeniedException;
import com.dbdr.security.exception.AuthenticationEntryPointException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception/*")
public class ExceptionController {

	@GetMapping("/entrypoint")
	public Result entryPointException() {
		throw new AuthenticationEntryPointException();
	}
	@GetMapping("/accessdenied")
	public Result accessdeniedException() {
		throw new AccessDeniedException();
	}
}
