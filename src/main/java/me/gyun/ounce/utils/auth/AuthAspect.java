package me.gyun.ounce.utils.auth;


import lombok.extern.slf4j.Slf4j;
import me.gyun.ounce.dto.logindto.UserDto;
import me.gyun.ounce.mapper.UserMapper;
import me.gyun.ounce.model.DefaultRes;
import me.gyun.ounce.service.JwtService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;


/**
 * 토큰 유효성 검사
 *
 * @Param pjp
 * @return
 * @throws Throwable
 */

@Slf4j
@Component
@Aspect
// 항상 @annotation 패키지 이름을 실제 사용한 annotation 경로로 맞춰야함
public class  AuthAspect {

    private final static String AUTHORIZATION = "token";
    /**
     * 실패 시 기본 반환 Response
     */
    private final static DefaultRes DEFAULT_RES = DefaultRes.builder().statusCode(401).responseMessage("인증 실패").build();
    private final static ResponseEntity<DefaultRes> RES_RESPONSE_ENTITY = new ResponseEntity<>(DEFAULT_RES, HttpStatus.UNAUTHORIZED);

    private final UserMapper userMapper;
    private final HttpServletRequest httpServletRequest;
    private final JwtService jwtService;

    /**
     * Repository 의존성 주입
     */
    public AuthAspect(UserMapper userMapper, HttpServletRequest httpServletRequest, JwtService jwtService) {
        this.userMapper = userMapper;
        this.httpServletRequest = httpServletRequest;
        this.jwtService = jwtService;
    }

    @Around("@annotation(me.gyun.ounce.utils.auth.Auth)")
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        final String jwt = httpServletRequest.getHeader(AUTHORIZATION);
        System.out.println(httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));

        // 토큰 존재 여부 확인
        if (jwt == null) {
            return RES_RESPONSE_ENTITY;
        }

        // 토큰 해독
        final JwtService.TOKEN token = jwtService.decode(jwt);

        // 토큰 검사
        if (token == null) {
            return RES_RESPONSE_ENTITY;
        } else {
            final UserDto user = userMapper.findByUserIdx(token.getUserIdx());
            // 유효 사용자 검사
            if (user == null) {
                return RES_RESPONSE_ENTITY;
            }
            return pjp.proceed(pjp.getArgs());
        }
    }
}

