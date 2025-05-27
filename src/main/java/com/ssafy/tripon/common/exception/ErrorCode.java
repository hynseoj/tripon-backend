package com.ssafy.tripon.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	
	// 400 BAD REQUEST
    INVALID_INPUT_VALUE(400, "잘못된 입력입니다."),
    MISSING_PARAMETER(400, "필수 파라미터가 누락되었습니다."),

    // 401 UNAUTHORIZED
    UNAUTHORIZED(401, "인증이 필요합니다."),
    ACCESS_TOKEN_EXPIRED(401, "토큰이 만료되었습니다."),

    // 403 FORBIDDEN
    FORBIDDEN(403, "접근 권한이 없습니다."),

    // 404 NOT FOUND
    USER_NOT_FOUND(404, "해당 유저를 찾을 수 없습니다."),
    ATTRACTIONS_NOT_FOUND(404, "관광지를 찾을 수 없습니다."),
    NOTICES_NOT_FOUND(404, "공지사항을 찾을 수 없습니다."),
    PLANS_NOT_FOUND(404, "여행 계획을 찾을 수 없습니다."),
    PLANDETAILS_NOT_FOUND(404, "여행 세부 계획을 찾을 수 없습니다."),
    PLAN_SHARE_LINK_NOT_FOUND(404, "해당 공유 링크를 찾을 수 없습니다."),
    REVIEWS_NOT_FOUND(404, "여행 후기를 찾을 수 없습니다."),
    REVIEWDETAILS_NOT_FOUND(404, "여행 세부 후기를 찾을 수 없습니다."),

    // 409 CONFLICT
    DUPLICATE_RESOURCE(409, "중복된 리소스입니다."),

    // 500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(500, "서버에 오류가 발생했습니다."),
    FILE_UPLOAD_FAILED(500, "사진 업로드에 실패했습니다.");

    private final int status;
    private final String message;

}
