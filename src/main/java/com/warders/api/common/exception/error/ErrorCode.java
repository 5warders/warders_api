package com.warders.api.common.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR.value(), "사용자 정보를 찾을 수 없습니다."),
    USER_DUPLICATE(HttpStatus.INTERNAL_SERVER_ERROR.value(), "이미 존재하는 사용자 입니다."),
    PASSWORD_MISMATCH(HttpStatus.INTERNAL_SERVER_ERROR.value(), "비밀번호가 올바르지 않습니다.")
    ;

    private final int status;
    private final String message;

}
