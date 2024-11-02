package com.morak.morak.chat.service;

import com.morak.morak.chat.dto.UserDto;
import com.morak.morak.chat.entity.User;
import com.morak.morak.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void saveUser(UserDto userDto) {
        userRepository.save(User.builder()
                .username(userDto.username())
                .profile(userDto.profile())
                .role(userDto.role())
                .build());
    }

    public UserDto getUser(String username) {
        return UserDto.entityToDto(userRepository.findByUsername(username));
    }
}
