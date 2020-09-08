package me.gyun.ounce.service;

import lombok.extern.slf4j.Slf4j;
import me.gyun.ounce.dto.User;
import me.gyun.ounce.mapper.UserMapper;
import me.gyun.ounce.model.DefaultRes;
import me.gyun.ounce.model.SignInModel;
import me.gyun.ounce.model.SignUpModel;
import me.gyun.ounce.utils.ResponseMessage;
import me.gyun.ounce.utils.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@Slf4j
public class AuthService {

    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    /**
     * UserMapper, JwtService, PasswordEncoder 생성자 의존성 주입
     *
     * @param UserMapper, JwtService, PasswordEncoder
     */
    public AuthService(UserMapper userMapper, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 회원가입
     *
     * @return DefaultRes
     */
    @Transactional
    public DefaultRes<JwtService.TokenRes> signUp(final SignUpModel signUpModel) {
        try {
            final User user = userMapper.findById(signUpModel.getId());

            if (user == null) {
                // 비밀번호 암호화
                String encodePassword = passwordEncoder.encode(signUpModel.getPassword());
                User user1 = new User(signUpModel.getId(), encodePassword, signUpModel.getEmail());

                userMapper.userInsert(user1);

                // 토큰 생성
                final JwtService.TokenRes tokenDto = new JwtService.TokenRes(jwtService.create(user1.getUserIdx()));
                return DefaultRes.res(StatusCode.OK, ResponseMessage.CREATED_USER, tokenDto);
            }

            // 이미 유저가 존재할 때
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.ALREADY_USER);

        } catch (Exception e) {
            //Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    /**
     * 로그인
     *
     * @return DefaultRes
     */
    public DefaultRes<JwtService.TokenRes> signIn(final SignInModel signInModel) {
        try {
            final User user = userMapper.findById(signInModel.getId());

            // 회원 정보가 존재하지 않거나, 아이디가 틀렸음
            if (user == null) {
                return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_FAIL);
            }

            // 로그인 성공
            if (passwordEncoder.matches(signInModel.getPassword(), user.getPassword())) {
                // 토큰 생성
                final JwtService.TokenRes tokenDto = new JwtService.TokenRes(jwtService.create(user.getUserIdx()));
                return DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, tokenDto);
            }

            // 비밀번호가 틀렸을 때
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_FAIL);
        } catch (Exception e) {
            log.info(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    /**
     * 중복아이디 체크
     *
     * @return DefaultRes
     */
    public DefaultRes checkLoginId(String id) {
        try {
            int userCount = userMapper.checkByLoginId(id);
            if (userCount == 0) {
                return DefaultRes.res(StatusCode.OK, ResponseMessage.AVAILABLE_LOGINID, true);
            }
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.ALREADY_USER, false);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }
}
