package com.example.kpopstore.services;

import com.example.kpopstore.entities.User;
import com.example.kpopstore.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import com.example.kpopstore.entities.User.Role;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String email = extractEmail(oAuth2User, registrationId);

        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User(email, email, "", Role.USER);
            return userRepository.save(newUser);
        });

        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())),
                oAuth2User.getAttributes(),
                "email"
        );
    }

    private String extractEmail(OAuth2User user, String provider) {
        Map<String, Object> attributes = user.getAttributes();
        return switch (provider) {
            case "google" -> (String) attributes.get("email");
            case "facebook" -> (String) attributes.get("email");
            case "github" -> (String) attributes.get("email");
            default -> throw new RuntimeException("Unknown provider");
        };
    }
}

