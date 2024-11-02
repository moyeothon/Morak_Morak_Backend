package com.morak.morak.chat.controller;

import com.morak.morak.chat.dto.UserDto;
import com.morak.morak.chat.service.RoomService;
import com.morak.morak.chat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RoomService roomService;

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(UserDto userDto) {
        userService.saveUser(userDto);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/role")
    public ResponseEntity<?> getUserRole(String username) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(username).role());
    }

    @GetMapping("/list")
    public ResponseEntity<?> getUsers(String roomId) {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.getUsersInRoom(roomId));
    }

}
