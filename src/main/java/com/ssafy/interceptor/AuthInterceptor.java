package com.ssafy.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

//@Component
public class AuthInterceptor implements HandlerInterceptor {

//  @Autowired
//  private JwtTokenProvider jwtTokenProvider;
//
//  @Override
//  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//          throws UserException {
//      final String token = request.getHeader("jwt");
//      log.info("preHandle: " + token);
//      if (!jwtTokenProvider.validateToken(token)) {
//          throw new UserException("권한이 없습니다", ErrorCode.INVALID_ACCESS);
//      }
//      request.setAttribute("userID", jwtTokenProvider.getId(token));
//      return true;
//  }
}
