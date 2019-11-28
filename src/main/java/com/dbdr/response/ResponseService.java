package com.dbdr.response;

import org.springframework.stereotype.Service;


@Service
public class ResponseService {

	public enum CommonResponse {
		SUCCESS(0,"SUCCESS"),
		FAIL(-1,"FAIL");

		int code;
		String msg;

		CommonResponse(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public int getCode() {
			return code;
		}

		public String getMsg() {
			return msg;
		}
	}

	/**
	 *
	 * @param <T> Http body에 담을 데이터
	 * @return
	 */
	public <T> Result<T> getResult(T data) {
		Result<T> result = new Result<>();
		result.setData(data);
		setSuccessResult(result);
		return result;
	}
	public Result getSuccessResult() {
		Result result = new Result();
		setSuccessResult(result);
		return result;
	}
	public Result getFailResult(int code, String msg) {
		Result result = new Result();
		result.setSuccess(false);
		result.setCode(code);
		result.setMsg(msg);
		return result;
	}

	private void setSuccessResult(Result result) {
		result.setSuccess(true);
		result.setCode(CommonResponse.SUCCESS.getCode());
		result.setMsg(CommonResponse.SUCCESS.getMsg());
	}
}
