package com.morak.morak.common.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode { // 반복적으로 사용될 Error 상태와 메세지, 코드를 정의
                        // 시스템에 비정상적인 상황이 발생했을 경우
    /**
     * 404 NOT FOUND
     */
    GAME_NOT_STARTED("게임이 아직 시작되지 않았습니다."),
    HINT_LIMIT("힌트 제공 횟수를 초과하였습니다."),
    WRONG_ANSWER("정답이 아닙니다. 다시 시도해 주세요"),
    /**
     * 500 INTERNAL SERVER ERROR
     */
    INTERNAL_SERVER_ERROR("알 수 없는 서버 에러가 발생했습니다. INTERNAL_SERVER_ERROR_500"),

    /**
     * 400 BAD REQUEST
     */
    VALIDATION_ERROR("잘못된 요청입니다. BAD_REQUEST_400");

    private final String message;


}
