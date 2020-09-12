package me.gyun.ounce.utils;

public class ResponseMessage {
    // 공통
    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String DB_ERROR = "데이터베이스 에러";
    public static final String NULL_VALUE = "필요한 값이 없습니다";

    // 유저
    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL = "로그인 실패";
    public static final String READ_USER = "회원 정보 조회 성공";
    public static final String ALREADY_USER = "회원이 존재합니다";
    public static final String NOT_FOUND_USER = "회원을 찾을 수 없습니다.";
    public static final String CREATED_USER = "회원 가입 성공";
    public static final String UPDATE_USER = "회원 정보 수정 성공";
    public static final String DELETE_USER = "회원 탈퇴 성공";
    public static final String AVAILABLE_LOGINID= "사용가능한 아이디입니다";

    // 프로필
    public static final String PROFILE_REGISTER_SUCCESS = "프로필 등록 성공입니다";
    public static final String PROFILE_REGISTER_POSSIBLE = "프로필 등록 가능합니다";
    public static final String PROFILE_REGISTER_IMPOSSIBLE = "프로필 등록 불가능합니다";
    public static final String PROFILE_UPDATE_SUCCESS = "프로필 수정 성공입니다";
    public static final String PROFILE_NOT_AUTHORITY = "프로필 수정 권한이 없습니다";
    public static final String PROFILE_CONVERSION_SUCCESS = "프로필 전환 성공입니다";

    // 메인화면
    public static final String PROFILE_MAIN_SUCCESS = "메인 프로필 조회 성공입니다";

    // 검색
    public static final String SUCCESS_SEARCH_USER = "유저검색 성공입니다";
    public static final String NO_SEARCH_RESULT = "검색결과가 존재하지 않습니다";

    // 리뷰
    public static final String FOOD_REVIEW_RESULT = "캣푸드 리뷰전체 조회 성공입니다";
    public static final String REVIEW_REGISTER_SUCCESS = "리뷰작성 성공입니다";
    public static final String REVIEW_NOT_DELETE = "리뷰 삭제권한이 없습니다";
    public static final String REVIEW_SUCCESS_DELETE = "리뷰를 삭제하였습니다";
}
