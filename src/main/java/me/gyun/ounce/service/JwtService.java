package me.gyun.ounce.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
            b.withIssuer(ISSUER);
            b.withClaim("userIdx", userIdx);
            return b.sign(Algorithm.HMAC256(SECRET));
        } catch (JWTCreationException jwtCreationException) {
            log.info(jwtCreationException.getLocalizedMessage());
        }
        return null;
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
