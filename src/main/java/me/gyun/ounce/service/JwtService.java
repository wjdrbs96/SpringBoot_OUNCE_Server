package me.gyun.ounce.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Slf4j
@Service
public class JwtService {

    @Value("${JWT.ISSUER}")
    private String ISSUER;

    @Value("${JWT.SECRET}")
    private String SECRET;


    /**
     * 토큰 생성
     *
     * @param userIdx 토큰에 담길 로그인한 사용자의 회원 고유 IDX
     * @return 토큰
     */

    public String create(final int userIdx) {
        try {
            JWTCreator.Builder b = JWT.create();
            // 토큰 발급자
            b.withIssuer(ISSUER);
            // 토큰 payload 작성, key - value 형식, 객체도 가능
            b.withClaim("userIdx", userIdx);
            // 토큰 만료날짜 지정
            b.withExpiresAt(expiresAt());
            return b.sign(Algorithm.HMAC256(SECRET));
        } catch (JWTCreationException jwtCreationException) {
            log.info(jwtCreationException.getLocalizedMessage());
        }
        return null;
    }

    private Date expiresAt()  {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        // 한달 24 * 31
        cal.add(Calendar.HOUR, 744);
        return cal.getTime();
    }

    public static class TOKEN {
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
