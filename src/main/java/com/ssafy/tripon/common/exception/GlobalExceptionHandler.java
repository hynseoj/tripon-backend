package com.ssafy.tripon.common.exception;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> handleCustomException(CustomException e, HttpServletRequest request) {
		ErrorCode errorCode = e.getErrorCode();
		log.error("custom 예외 발생: " + errorCode.getMessage());
		return ResponseEntity.status(errorCode.getStatus())
				.body(new ErrorResponse(errorCode.getStatus(), errorCode.getMessage(), request.getRequestURI()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleAllExceptions(Exception e, HttpServletRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(),
				request.getRequestURI());
		log.error("알 수 없는 예외 발생 uri: {} {}, ", request.getMethod(), request.getRequestURI(), e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}

	@Override
	protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException e, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		return new ResponseEntity<>(status);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body, HttpHeaders headers,
			HttpStatusCode statusCode, WebRequest webRequest) {
		HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
		log.error("spring 예외 발생 uri: {} {}, ", request.getMethod(), request.getRequestURI(), e);
		requestLogging(request);
		return ResponseEntity.status(statusCode)
				.body(new ErrorResponse(statusCode.value(), e.getMessage(), request.getRequestURI()));

	}

	private void requestLogging(HttpServletRequest request) {
		log.info("request header: {}", getHeaders(request));
		log.info("request query string: {}", getQueryString(request));
		log.info("request body: {}", getRequestBody(request));
	}

	private Map<String, Object> getHeaders(HttpServletRequest request) {
		Map<String, Object> headerMap = new HashMap<>();
		Enumeration<String> headerArray = request.getHeaderNames();
		while (headerArray.hasMoreElements()) {
			String headerName = headerArray.nextElement();
			headerMap.put(headerName, request.getHeader(headerName));
		}
		return headerMap;
	}

	private String getQueryString(HttpServletRequest httpRequest) {
		String queryString = httpRequest.getQueryString();
		if (queryString == null) {
			return " - ";
		}
		return queryString;
	}

	// TODO: 처음부터 캐싱하기 위해 filter에서 HttpServletRequest를 ContentCachingRequestWrapper로 감싸줘야 함
	private String getRequestBody(HttpServletRequest request) {
		var wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
		if (wrapper == null) {
			return " - ";
		}
		try {
			// body 가 읽히지 않고 예외처리 되는 경우에 캐시하기 위함
			wrapper.getInputStream().readAllBytes();
			byte[] buf = wrapper.getContentAsByteArray();
			if (buf.length == 0) {
				return " - ";
			}
			return new String(buf, wrapper.getCharacterEncoding());
		} catch (Exception e) {
			return " - ";
		}
	}
}
