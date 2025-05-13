package com.ssafy.tripon.common.exception;

public record ErrorResponse(
	int status,
	String message,
	String path
) {
	
}
