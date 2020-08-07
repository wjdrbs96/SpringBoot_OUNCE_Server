package me.gyun.ounce.service;

import me.gyun.ounce.dto.User;
import me.gyun.ounce.mapper.UserMapper;
import me.gyun.ounce.model.DefaultRes;
import me.gyun.ounce.model.SignUpModel;
import me.gyun.ounce.utils.ResponseMessage;
import me.gyun.ounce.utils.SHA512EncryptUtils;
import me.gyun.ounce.utils.StatusCode;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserMapper userMapper;
    private final JwtService jwtService;

    public AuthService(UserMapper userMapper, JwtService jwtService) {
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    public DefaultRes<JwtService.TokenRes> signUp(final SignUpModel signUpModel) {
        final User user = userMapper.findByIdAndPassword(signUpModel.getId(), signUpModel.getPassword());

        // 회원이 이미 존재할 때
        if (user != null) {
            return DefaultRes.res(StatusCode.OK, ResponseMessage.ALREADY_USER);
        }

        String salt = SHA512EncryptUtils.encrypt(signUpModel.getPassword());
        userMapper.save(signUpModel.getId(), signUpModel.getPassword(), salt, signUpModel.getEmail());

        final JwtService.TokenRes tokenDto = new JwtService.TokenRes(jwtService.create(user.getUserIdx()));

        return DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, tokenDto);
    }
}
