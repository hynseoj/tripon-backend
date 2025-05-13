package com.ssafy.tripon.common.exception;

public class CustomException extends RuntimeException{
	private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());  // 메시지는 여기에 자동으로 들어감
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
