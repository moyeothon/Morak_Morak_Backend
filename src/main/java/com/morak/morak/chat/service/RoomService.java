package com.morak.morak.chat.service;

import com.morak.morak.chat.dto.UserDto;
import com.morak.morak.chat.entity.Room;
import com.morak.morak.chat.entity.User;
import com.morak.morak.chat.repository.RoomRepository;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public Room createRoom(String roomId) {
        Room room = new Room(roomId);
        return roomRepository.save(room);
    }

    public User addUserToRoom(String roomId, UserDto userDto) {
        Room room = roomRepository.findByRoomId(roomId);
        User user = User.builder()
                .username(userDto.username())
                .profile(userDto.profile())
                .role(userDto.role())
                .build();
        room.addUser(user);
        roomRepository.save(room);
        return user;
    }

    public List<User> getUsersInRoom(String roomId) {
        Room room = roomRepository.findByRoomId(roomId);
        return room != null ? room.getUsers() : Collections.emptyList();
    }
}
