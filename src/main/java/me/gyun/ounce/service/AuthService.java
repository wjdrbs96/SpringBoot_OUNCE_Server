package me.gyun.ounce.service;

import me.gyun.ounce.dto.User;
import me.gyun.ounce.mapper.UserMapper;
import me.gyun.ounce.model.DefaultRes;
import me.gyun.ounce.model.SignUpModel;
import me.gyun.ounce.utils.ResponseMessage;
import me.gyun.ounce.utils.StatusCode;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserMapper userMapper, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public DefaultRes<JwtService.TokenRes> signUp(final SignUpModel signUpModel) {
        final User user = userMapper.findByIdAndPassword(signUpModel.getId(), signUpModel.getPassword());

        // 회원이 이미 존재할 때
        if (user != null) {
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.ALREADY_USER);
        }

        System.out.println(passwordEncoder.encode(signUpModel.getPassword()));

        String salt = BCrypt.gensalt();
        int userIdx = userMapper.save(signUpModel.getId(), BCrypt.hashpw(signUpModel.getPassword(), salt), salt, signUpModel.getEmail());

        final JwtService.TokenRes tokenDto = new JwtService.TokenRes(jwtService.create(userIdx));

        return DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, tokenDto);
    }
}
