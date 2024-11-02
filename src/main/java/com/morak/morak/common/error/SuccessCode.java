package com.morak.morak.common.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {   // Success 상태와 메세지, 코드를 정의
    /**
     * 200 OK   (요청 성공
     */
    GET_SUCCESS("성공적으로 조회했습니다."),

    /**
     * 201 CREATED (POST의 결과 상태)
     */
    GAME_STARTED("게임이 시작되었습니다."),
    CORRECT_ANSWER("정답입니다! 게임이 종료되었습니다."),
    GAME_RESET("게임이 리셋되었습니다.");

    private final String message;

}
