package com.example.oauth2demo.controller;

import com.example.oauth2demo.model.User;
import com.example.oauth2demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
    
    @GetMapping("/loginSuccess")
    public String loginSuccess(OAuth2AuthenticationToken authentication) {
        
    	OAuth2User oAuth2User = authentication.getPrincipal();
        
        String email = oAuth2User.getAttribute("email");

        if (email != null && !email.isEmpty()) {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                user = new User(email);
                userRepository.save(user);
            }
            return "Login successful! Email: " + email;
        }
        return "Error: Email not found!";
    }
}
