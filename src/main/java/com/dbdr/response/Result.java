package com.dbdr.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {
	private boolean success;
	private int code;
	private String msg;
	private T data;
}
