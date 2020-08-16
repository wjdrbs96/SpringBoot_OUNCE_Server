package me.gyun.ounce.service;

import me.gyun.ounce.dto.User;
import me.gyun.ounce.mapper.UserMapper;
import me.gyun.ounce.model.DefaultRes;
import me.gyun.ounce.model.SignInModel;
import me.gyun.ounce.model.SignUpModel;
import me.gyun.ounce.utils.ResponseMessage;
import me.gyun.ounce.utils.StatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final SaltService saltService;

    public AuthService(UserMapper userMapper, JwtService jwtService, PasswordEncoder passwordEncoder, SaltService saltService) {
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.saltService = saltService;
    }

    // 회원가입
    public DefaultRes<JwtService.TokenRes> signUp(final SignUpModel signUpModel) {
        final User user = userMapper.findById(signUpModel.getId());

        // 회원이 이미 존재할 때
        if (user != null) {
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.ALREADY_USER);
        }
        String salt = saltService.genSalt();

        int userIdx = userMapper.save(signUpModel.getId(), saltService.encodePassword(salt, signUpModel.getPassword()),salt, signUpModel.getEmail());

        final JwtService.TokenRes tokenDto = new JwtService.TokenRes(jwtService.create(userIdx));

        return DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, tokenDto);
    }

    // 로그인
    public DefaultRes<JwtService.TokenRes> signIn(final SignInModel signInModel) {
        final User user = userMapper.findById(signInModel.getId());

        if (passwordEncoder.matches(signInModel.getPassword(), user.getPassword()) && user != null) {
            // 토큰 생성
            final JwtService.TokenRes tokenDto = new JwtService.TokenRes(jwtService.create(user.getUserIdx()));
            return DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, tokenDto);
        }

        return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_FAIL);
    }
}
