package org.goormton.darktourism.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {
    
    MEMBER_INFORMATION_NOT_FOUND(HttpStatus.NOT_FOUND, "유저 정보가 없습니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "토큰이 없습니다."),
    CANNOT_SET_TOKEN(HttpStatus.BAD_REQUEST, "토큰 상태를 변경할 수 없습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효한 토큰이 아닙니다."),

    // MEMBER
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다."),
    DUPLICATED_NICKNAME(HttpStatus.BAD_REQUEST, "이미 가입된 닉네임입니다."),

    // PLACE
    PLACE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 장소를 찾을 수 없습니다."),
    PLACE_ALREADY_VISITED(HttpStatus.BAD_REQUEST, "이미 방문한 장소입니다.");

    private final HttpStatus httpStatus;
    private final String detail;

    ExceptionType(final HttpStatus httpStatus, final String detail) {
        this.httpStatus = httpStatus;
        this.detail = detail;
    }
}
