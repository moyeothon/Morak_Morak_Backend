package com.morak.morak.gpt.controller;

import com.morak.morak.gpt.dto.UserGptRequestDto;
import com.morak.morak.gpt.service.OpenAiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/gpt")
public class OpenAiController {

    private final OpenAiService openAiService;

    @Operation(summary = "게임 시작", description = "스무고개 게임을 시작합니다.")
    @ApiResponse(responseCode = "200", description = "게임이 성공적으로 시작되었습니다.")
    @PostMapping("/start")
    public ResponseEntity<String> startGame() {
        openAiService.startGame();
        return ResponseEntity.ok("게임이 시작되었습니다.");
    }

    @Operation(summary = "사용자의 gpt 요청", description = "사용자가 gpt에게 주관식 질문 요청을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성을 성공했습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/request")
    public ResponseEntity<String> getChatTalk(@RequestBody @Valid UserGptRequestDto userGptRequestDto) {
        String response = openAiService.askQuestion(userGptRequestDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "힌트 요청", description = "현재 키워드에 대한 힌트를 요청합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성을 성공했습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/hint")
    public ResponseEntity<String> getHint() {
        String hint = openAiService.getHint();
        return ResponseEntity.ok(hint);
    }

    @Operation(summary = "게임 리셋", description = "게임을 리셋합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성을 성공했습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/reset")
    public ResponseEntity<String> resetGame() {
        openAiService.resetGame();
        return ResponseEntity.ok("게임이 리셋되었습니다.");
    }

    @Operation(summary = "사용자의 질문 전체 조회", description = "한 게임 당 사용자의 질문과 gpt의 yes or no 응답을 전체 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성을 성공했습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @GetMapping("/chat/all")
    public List<String> findAllChat() {
        return openAiService.getConversationHistory();
    }
}
