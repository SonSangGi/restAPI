package com.dbdr;

import io.jsonwebtoken.Jwts;

import java.util.ArrayList;
import java.util.List;

public class JWTSample {
	public static void main(String[] args) {
		List<String> authList = new ArrayList<>();
		authList.add("manager");
		authList.add("admin");
		authList.add("user");

	}
}
