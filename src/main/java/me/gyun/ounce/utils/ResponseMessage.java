package me.gyun.ounce.utils;

public class ResponseMessage {
    // 공통
    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String DB_ERROR = "데이터베이스 에러";
    public static final String NULL_VALUE = "필요한 값이 없습니다";
    public static final String UNAUTHORIZED = "권한이 없습니다";
    public static final String WRONG_VALUE = "올바르지 않은 값입니다";
    public static final String TOKEN_REQUEST = "토큰 재발급";

    // 유저
    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL = "로그인 실패";
    public static final String READ_USER = "회원 정보 조회 성공";
    public static final String ALREADY_USER = "회원이 존재합니다";
    public static final String NOT_FOUND_USER = "회원을 찾을 수 없습니다.";
    public static final String CREATED_USER = "회원 가입 성공";
    public static final String UPDATE_USER = "회원 정보 수정 성공";
    public static final String DELETE_USER = "회원 탈퇴 성공";

    // 프로필
    public static final String PROFILE_REGISTER = "프로필 등록 성공";

}
