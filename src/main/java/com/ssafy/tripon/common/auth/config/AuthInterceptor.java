package com.ssafy.tripon.common.auth.config;

import static com.ssafy.tripon.common.exception.ErrorCode.FORBIDDEN;
import static com.ssafy.tripon.common.exception.ErrorCode.UNAUTHORIZED;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.ssafy.tripon.common.auth.JwtTokenProvider;
import com.ssafy.tripon.common.auth.Token;
import com.ssafy.tripon.common.auth.TokenBlacklistService;
import com.ssafy.tripon.common.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private static final String AUTHENTICATION_TYPE = "Bearer ";
    private static final int BEARER_PREFIX_LENGTH = 7;

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklistService blacklistService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // preflight 요청은 인증 없이 허용
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String header = request.getHeader(AUTHORIZATION);

        if (header == null || !header.startsWith(AUTHENTICATION_TYPE)) {
            throw new CustomException(UNAUTHORIZED);
        }

        Token token = new Token(header.substring(BEARER_PREFIX_LENGTH));

        jwtTokenProvider.validateToken(token);
        if (blacklistService.isBlacklisted(jwtTokenProvider.getJti(token))) {
            throw new CustomException(FORBIDDEN);
        }

        request.setAttribute("memberEmail", jwtTokenProvider.getMemberEmail(token));
        request.setAttribute("jwtToken", token);

        return true;
    }
}
