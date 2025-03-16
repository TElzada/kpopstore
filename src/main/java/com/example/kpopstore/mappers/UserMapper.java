package com.example.kpopstore.mappers;

import com.example.kpopstore.DTO.UserDTO;
import com.example.kpopstore.entities.User;
import com.example.kpopstore.entities.User.Role;

import java.util.UUID;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(
                user.getId().toString(),  // Преобразуем UUID в строку
                user.getUsername(),
                user.getEmail(),
                user.getRole().toString(), // Преобразуем Role в строку
                user.getPassword()
        );
    }

    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        UUID id = UUID.fromString(userDTO.getId());
        Role role = Role.valueOf(userDTO.getRole());
        return new User(
                userDTO.getUsername(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                role
        );
    }
}


