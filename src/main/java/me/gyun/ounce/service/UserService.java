package me.gyun.ounce.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gyun.ounce.dto.ResponseDto;
import me.gyun.ounce.dto.user.SignInDto;
import me.gyun.ounce.dto.user.SignUpDto;
import me.gyun.ounce.dto.user.TokenDto;
import me.gyun.ounce.dto.user.UserDto;
import me.gyun.ounce.mapper.UserMapper;
import me.gyun.ounce.utils.ResponseMessage;
import me.gyun.ounce.utils.StatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * 회원가입
     * @param SignUpDto
     */
    public ResponseDto<?> userSignUp(SignUpDto signUpDto) {
        try {
            UserDto byLoginId = userMapper.findByLoginId(signUpDto.getId());
            if (byLoginId != null) {
                return ResponseDto.res(StatusCode.BAD_REQUEST, ResponseMessage.ALREADY_USER);
            }

            String encodePassword = passwordEncoder.encode(signUpDto.getPassword());
            UserDto userDto = new UserDto(signUpDto.getId(), encodePassword, signUpDto.getEmail());
            userMapper.signUp(userDto);
            TokenDto tokenDto = jwtService.create(userDto.getUserIdx());
            userMapper.userUpdate(tokenDto.getRefreshToken(), userDto.getUserIdx());
            return ResponseDto.res(StatusCode.OK, ResponseMessage.CREATED_USER, tokenDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseDto.res(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    /**
     * 로그인
     * @param signInDto
     * @return
     */
    public ResponseDto userSignIn(SignInDto signInDto) {
        try {
            UserDto byLoginId = userMapper.findByLoginId(signInDto.getId());
            if (byLoginId == null) {
                return ResponseDto.res(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER);
            }

            UserDto userDto = new UserDto(signInDto.getId(), signInDto.getPassword());
            if (passwordEncoder.matches(signInDto.getPassword(), byLoginId.getPassword())) {
                TokenDto tokenDto = jwtService.create(byLoginId.getUserIdx());
                return ResponseDto.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, tokenDto);
            }

            return ResponseDto.res(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_FAIL);

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseDto.res(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
        }
    }
}
