package com.example.kpopstore.mappers;

import com.example.kpopstore.DTO.UserDTO;
import com.example.kpopstore.entities.User;
import com.example.kpopstore.entities.User.Role;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void testToDTO() {
        User user = new User("testUser", "test@example.com", "password123", Role.USER);
        user.setId(UUID.randomUUID());

        UserDTO userDTO = UserMapper.toDTO(user);

        assertNotNull(userDTO);
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getRole().toString(), userDTO.getRole());
    }

    @Test
    void testToEntity() {
        String id = UUID.randomUUID().toString();

        UserDTO userDTO = new UserDTO(id, "testUser", "test@gmail.com", "USER", "password123");

        assertThat(userDTO.getId()).isEqualTo(id);
        assertThat(userDTO.getUsername()).isEqualTo("testUser");
        assertThat(userDTO.getEmail()).isEqualTo("test@gmail.com");
        assertThat(userDTO.getRole()).isEqualTo("USER");
        assertThat(userDTO.getPassword()).isEqualTo("password123");
    }
}

