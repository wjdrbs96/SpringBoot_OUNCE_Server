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

        // salt Random 생성 (방법 2)
        String salt = saltService.genSalt();

        // 비밀번호 암호화 방법1 ( passwordEncoder 를 구현하는 BCryptPasswordEncoder 클래스의 encode 메소드에서 salt를 제공 )
        System.out.println("Password Encode: " + passwordEncoder.encode(signUpModel.getPassword()));

        // 비밀번호 암호화 방법2
        int userIdx = userMapper.save(signUpModel.getId(), saltService.encodePassword(salt, signUpModel.getPassword()), salt, signUpModel.getEmail());

        final JwtService.TokenRes tokenDto = new JwtService.TokenRes(jwtService.create(userIdx));

        return DefaultRes.res(StatusCode.OK, ResponseMessage.CREATED_USER, tokenDto);
    }

    // 로그인
    public DefaultRes<JwtService.TokenRes> signIn(final SignInModel signInModel) {
        final User user = userMapper.findById(signInModel.getId());

        // 회원 정보가 존재하지 않거나, 아이디가 틀렸음
        if (user == null) {
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_FAIL);
        }

        // 다른 방법
//        String salt = user.getSalt();
//        String password = saltService.encodePassword(salt, signInModel.getPassword());
//        if (password.equals(signInModel.getPassword())) {
//            // 토큰 생성
//            final JwtService.TokenRes tokenDto = new JwtService.TokenRes(jwtService.create(user.getUserIdx()));
//            return DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, tokenDto);
//        }

        // 로그인 성공
        if (passwordEncoder.matches(signInModel.getPassword(), user.getPassword())) {
            // 토큰 생성
            final JwtService.TokenRes tokenDto = new JwtService.TokenRes(jwtService.create(user.getUserIdx()));
            return DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, tokenDto);
        }

        // 비밀번호가 틀렸을 때
        return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_FAIL);
    }
}
