package com.dbdr.controller;

import com.dbdr.domain.Sample;
import com.dbdr.mapper.SampleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SampleController {

	private final SampleMapper sampleMapper;

	@GetMapping("/sample")
	public Sample sample() {

		return new Sample();
	}
}
