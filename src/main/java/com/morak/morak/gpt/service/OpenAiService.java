package com.morak.morak.gpt.service;

import com.morak.morak.common.error.ErrorCode;
import com.morak.morak.common.exception.NotFoundException;
import com.morak.morak.gpt.dto.GptResponseDto;
import com.morak.morak.gpt.dto.UserGptRequestDto;
import lombok.RequiredArgsConstructor;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OpenAiService {

    private final ChatClient chatClient;
    private String defaultKeyword;
    private final List<String> conversationHistory = new ArrayList<>(); // 대화 기록 저장 리스트
    private int hintCount = 0;

    @Transactional
    public void startGame() {
        String initialPrompt = String.format(
                "게임 키워드 주제는 '문화', '낭만', '모임' 중 하나입니다. " +
                        "세부적으로, '낭만'을 선택하면 장소나 활동을 기반으로 키워드를 선정하세요. 예를 들어 '해변', '전망대', '데이트'와 같이 낭만적인 장소나 활동으로 키워드를 설정해 주세요. " +
                        "'문화'를 선택하면 예술, 축제, 전통과 관련된 키워드를 만드세요. 예를 들어 '미술관' 처럼 특정 장소를 표현하는 키워드로 설정하세요. " +
                        "'모임'을 선택하면 사람들이 모이는 장소나 목적을 표현하는 구체적인 키워드를 선택해 주세요. 예를 들어 '파티', '결혼식', '동창회'와 같이 '모임'을 직접적으로 나타내지 않고 장소나 이유로 표현해 주세요. " +
                        "주제는 반드시 하나만 선택하여 관련된 구체적인 키워드 단어 하나를 만들어 주세요." + "키워드가 두 단어 이상이면 안 됩니다."
        );
        ChatResponse response = callChatClient(initialPrompt);
        defaultKeyword = response.getResult().getOutput().getContent();

    }

    // ChatGPT API 요청하기 - 사용자의 주관식 질문
    @Transactional
    public GptResponseDto askQuestion(UserGptRequestDto userGptRequestDto) {
        String userQuestion = userGptRequestDto.getQuestion();

        if (defaultKeyword == null) {
            throw new NotFoundException(ErrorCode.GAME_NOT_STARTED, ErrorCode.GAME_NOT_STARTED.getMessage());
        }

        // 사용자가 입력한 질문이 정답인지 체크
        if (userQuestion.startsWith("정답! ")) {
            String answer = userQuestion.substring(4).trim(); // "정답! "를 제거하고 키워드만 남김
            // 키워드 확인 (한글 입력에 대한 정확한 비교)
            if (answer.equals(defaultKeyword)) {
                conversationHistory.add("사용자: " + userQuestion); // 대화 기록에 사용자 질문 추가
                String endGameMessage = endGame();  // 게임 종료 처리
                conversationHistory.add("Gpt: " + endGameMessage);
                return new GptResponseDto(endGameMessage);
            } else {
                conversationHistory.add("사용자: " + userQuestion);
                String wrongAnswerMessage = "틀렸습니다. 다시 시도해주세요";
                conversationHistory.add("GPT: " + wrongAnswerMessage);
                return new GptResponseDto(wrongAnswerMessage);
            }
        }

        String prompt = String.format(
                "사용자가 정답을 맞추는 스무고개 게임을 진행 중입니다. 키워드는 '%s'입니다. " +
                        "사용자 질문: \"%s\"에 대해 Yes 또는 No로 대답해 주세요.",
                defaultKeyword, userQuestion
        );

        ChatResponse response = callChatClient(prompt);

        // 대화 기록에 사용자 질문 및 GPT 응답 추가
        conversationHistory.add("사용자: " + userQuestion);
        conversationHistory.add("GPT: " + response);

        return GptResponseDto.fromChatResponse(response);
    }

    // 힌트 제공 메서드
    @Transactional
    public GptResponseDto getHint() {
        if (defaultKeyword == null) {
            throw new NotFoundException(ErrorCode.GAME_NOT_STARTED, ErrorCode.GAME_NOT_STARTED.getMessage());
        }

        if (hintCount >= 5) {
            throw new NotFoundException(ErrorCode.HINT_LIMIT, ErrorCode.HINT_LIMIT.getMessage()); // 힌트 제공 제한
        }

        String hintPrompt = String.format(
                "현재 키워드는 '%s'입니다. 세 주제 중 설정한 키워드에 관해 키워드는 노출되지 않고 키워드가 어떤 것인지 설명만 출력해줘. 출력 양식 '힌트:' ",
                defaultKeyword
        );

        ChatResponse response = callChatClient(hintPrompt);
        hintCount++;

        return GptResponseDto.fromChatResponse(response);
    }

    // ChatClient 호출을 위한 메서드
    private ChatResponse callChatClient(String prompt) {
        return chatClient.call(
                new Prompt(prompt,
                        OpenAiChatOptions.builder()
                                .withTemperature(0.4F)
                                .withFrequencyPenalty(0.7F)
                                .withModel("gpt-4o-mini")
                                .build()
                )
        );
    }

    // 게임 리셋 메서드
    @Transactional
    public void resetGame() {
        defaultKeyword = null;
        conversationHistory.clear();
        hintCount = 0;
    }

    // 게임 종료 메서드
    @Transactional
    public String endGame() {
        String message = "정답입니다! 게임이 종료되었습니다.";
        resetGame();  // 게임 초기화
        return message;
    }

    // 대화 기록 조회 메서드
    public List<String> getConversationHistory() {
        return new ArrayList<>(conversationHistory);
    }
}
