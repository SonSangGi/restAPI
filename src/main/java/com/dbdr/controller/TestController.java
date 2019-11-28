package com.dbdr.controller;

import com.dbdr.domain.User;
import com.dbdr.mapper.UserMapper;
import com.dbdr.response.ResponseService;
import com.dbdr.response.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

	private final UserMapper userMapper;
	private final ResponseService responseService;

	@GetMapping("/admin")
	public Result admin() throws Exception {

		return responseService.getSuccessResult();
	}

	@PostMapping("/admin")
	public Result adminP() throws Exception {

		return responseService.getSuccessResult();
	}

	@GetMapping("/test")
	public Result sample(String id) throws Exception {

		userMapper.checkUserId(id);
		return responseService.getResult(id);
	}

}
