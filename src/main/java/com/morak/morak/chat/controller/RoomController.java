package com.morak.morak.chat.controller;

import com.morak.morak.chat.dto.UserDto;
import com.morak.morak.chat.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/create")
    public ResponseEntity<?> createRoom(String roomId) {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.createRoom(roomId));
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUserInRoom(String roomId, UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.addUserToRoom(roomId, userDto));
    }

}
