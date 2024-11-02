package com.morak.morak.chat.dto;

import com.morak.morak.chat.entity.RoleType;
import com.morak.morak.chat.entity.User;
import lombok.Builder;

@Builder
public record UserDto(
        String profile,
        String username,
        RoleType role
) {
    public static UserDto entityToDto(User user) {
        return UserDto.builder()
                .profile(user.getProfile())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
