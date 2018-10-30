package com.aboo.vbbs.base.model;

import lombok.Data;

@Data
public class Result<T> {

	private boolean success;
	private int code;
	private String msg;
	private T data;

	public static Result<Object> success() {
		return success(null);
	}

	public static <T> Result<T> success(T data) {
		Result<T> result = new Result<>();
		result.setSuccess(true);
		result.setCode(200);
		result.setData(data);
		return result;
	}

	public static Result<Object> error() {
		return error(null);
	}

	public static  Result<Object> error(String msg) {
		return error(500, msg);
	}

	public static Result<Object> error(int code, String msg) {
		Result<Object> result = new Result<>();
		result.setSuccess(false);
		result.setCode(code);
		result.setMsg(msg);
		return result;
	}

}
