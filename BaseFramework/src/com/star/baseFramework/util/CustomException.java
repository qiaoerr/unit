package com.star.baseFramework.util;

/**
 * 自定义错误
 * 
 */
public class CustomException extends Exception {
	private static final long serialVersionUID = -8689266658212033997L;
	private int statusCode = -1;

	public CustomException(String msg) {
		super(msg);
	}

	public CustomException(Exception cause) {
		super(cause);
	}

	public CustomException(String msg, int statusCode) {
		super(msg);
		this.statusCode = statusCode;
	}

	public CustomException(String msg, Exception cause) {
		super(msg, cause);
	}

	public CustomException(String msg, Exception cause, int statusCode) {
		super(msg, cause);
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return this.statusCode;
	}

	public CustomException() {
		super();
	}

	public CustomException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public CustomException(Throwable throwable) {
		super(throwable);
	}

	public CustomException(int statusCode) {
		super();
		this.statusCode = statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
