package me.gyun.ounce.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import me.gyun.ounce.dto.user.TokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

import static com.auth0.jwt.JWT.require;

@Slf4j
@Service
public class JwtService {

    @Value("${JWT.ISSUER}")
    private String ISSUER;

    @Value("${JWT.SECRET}")
    private String SECRET;

    @Value("${JWT.REFRESH_ISSUER}")
    private String REFRESH_ISSUER;

    @Value("${JWT.REFRESH.SECRET}")
    private String REFRESH_SECRET;


    /**
     * 토큰 생성
     *
     * @param userIdx 토큰에 담길 로그인한 사용자의 회원 고유 IDX
     * @return 토큰
     */

    public TokenDto create(final int userIdx) {
        try {
            JWTCreator.Builder accessToken = JWT.create();
            JWTCreator.Builder refreshToken = JWT.create();
            // 토큰 발급자
            refreshToken.withIssuer(REFRESH_ISSUER);
            refreshToken.withClaim("userIdx", userIdx);
            refreshToken.withExpiresAt(refreshExpiresAt());
            // 토큰 payload 작성, key - value 형식, 객체도 가능
            accessToken.withIssuer(ISSUER);
            accessToken.withClaim("userIdx", userIdx);
            // 토큰 만료날짜 지정
            accessToken.withExpiresAt(accessExpiresAt());
            TokenDto tokenDto = new TokenDto(accessToken.sign(Algorithm.HMAC256(SECRET)), refreshToken.sign(Algorithm.HMAC256(REFRESH_SECRET)));
            return tokenDto;
        } catch (JWTCreationException jwtCreationException) {
            log.info(jwtCreationException.getLocalizedMessage());
        }
        return null;
    }

    private Date accessExpiresAt()  {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR, 2);
        return cal.getTime();
    }

    private Date refreshExpiresAt() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_WEEK, 2);
        return cal.getTime();
    }

    /**
     * 토큰 해독
     *
     * @param token 토큰
     * @return 로그인한 사용자의 회원 고유 IDX
     */

    public TOKEN decode(final String token) {
        try {
            // 토큰 해독 객체 생성
            final JWTVerifier jwtVerifier = require(Algorithm.HMAC256(SECRET)).withIssuer(ISSUER).build();
            // 토큰 검증
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            // 토큰 payload 반환, 정상적인 토큰이라면 토큰 사용자 고유 ID, 아니라면 -1
            return new TOKEN(decodedJWT.getClaim("userIdx").asLong().intValue());
        } catch (JWTVerificationException jve) {
            log.error(jve.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new TOKEN();
    }

    public TOKEN refreshDecode(final String refreshToken) {
        try {
            final JWTVerifier jwtVerifier = require(Algorithm.HMAC256(REFRESH_SECRET)).withIssuer(REFRESH_ISSUER).build();
            DecodedJWT refreshTokenVerify = jwtVerifier.verify(refreshToken);
            return new TOKEN(refreshTokenVerify.getClaim("userIdx").asLong().intValue());
        } catch (JWTVerificationException jve) {
            log.error(jve.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new TOKEN();
    }

    /**
     * 권한 확인
     *
     * @param token, Authorization
     * @param userIdx 사용자 고유 번호
     * @return boolean
     */
    public boolean checkAuth(final String token, final int userIdx) {
        return decode(token).getUserIdx() == userIdx;
    }

    public static class TOKEN {
        //토큰에 담길 정보 필드
        //초기값을 -1로 설정함으로써 로그인 실패시 -1반환
        private int userIdx = -1;

        public TOKEN() {

        }

        public TOKEN(final int userIdx) {
            this.userIdx = userIdx;
        }

        public int getUserIdx() {
            return userIdx;
        }
    }

    public static class TokenRes {
        private String token;

        public TokenRes() {

        }

        public TokenRes(final String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
