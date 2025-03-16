package com.example.kpopstore.controllers;

import com.example.kpopstore.entities.User;
import com.example.kpopstore.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
public class UserControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void shouldReturn400WhenUserValidationFails() throws Exception {
        User user = new User("", "", "", null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"\",\"email\":\"\",\"password\":\"\",\"role\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.username").value("Username is required"))
                .andExpect(jsonPath("$.validationErrors.email").value("Email is required"))
                .andExpect(jsonPath("$.validationErrors.password").value("Password is required"))
                .andExpect(jsonPath("$.validationErrors.role").value("Role is required"));
    }
}

