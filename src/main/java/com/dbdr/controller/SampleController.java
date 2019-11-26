package com.dbdr.controller;

import com.dbdr.domain.Sample;
import com.dbdr.mapper.SampleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SampleController {

	private final SampleMapper sampleMapper;

	@PostMapping("/sample")
	public Sample sample() {

		String pwd = "zxcv1234";

		return new Sample();
	}

	@GetMapping("/hi/hi")
	public String hi() {
		return "hi";
	}


}
