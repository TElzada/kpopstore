package com.example.kpopstore.controllers;

import com.example.kpopstore.entities.User;
import com.example.kpopstore.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;
    private List<User> userList;

    @BeforeEach
    public void setUp() {

        user = new User("testUser", "test@example.com", "password", User.Role.USER);
        user.setId(UUID.randomUUID());

        userList = new ArrayList<>();
        userList.add(user);
    }

    @Test
    public void getUserById_ShouldReturnUser() throws Exception {

        when(userService.getUserById(eq(user.getId()))).thenReturn(user);

        mockMvc.perform(get("/api/users/{userId}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void getAllUsers_ShouldReturnListOfUsers() throws Exception {

        when(userService.getAllUsers()).thenReturn(userList);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testUser"));
    }

    @Test
    public void createUser_ShouldReturnCreatedUser() throws Exception {

        when(userService.createUser(eq(user.getUsername()), eq(user.getEmail()), eq(user.getPassword()), eq(user.getRole())))
                .thenReturn(user);

        mockMvc.perform(post("/api/users")
                        .param("username", "testUser")
                        .param("email", "test@example.com")
                        .param("password", "password")
                        .param("role", "USER")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void updateUser_ShouldReturnUpdatedUser() throws Exception {

        when(userService.updateUser(eq(user.getId()), eq(user.getEmail()), eq(user.getPassword()), eq(user.getRole())))
                .thenReturn(user);

        mockMvc.perform(put("/api/users/{userId}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"testUser\", \"email\": \"test@example.com\", \"password\": \"password\", \"role\": \"USER\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void deleteUser_ShouldReturnNoContent() throws Exception {

        when(userService.getUserById(eq(user.getId()))).thenReturn(user);

        mockMvc.perform(delete("/api/users/{userId}", user.getId()))
                .andExpect(status().isNoContent());
    }

}

