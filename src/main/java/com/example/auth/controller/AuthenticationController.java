package com.example.auth.controller;

import com.example.auth.config.TokenService;
import com.example.auth.domain.dto.AuthenticationDTO;
import com.example.auth.domain.dto.LoginResponseDTO;
import com.example.auth.domain.dto.RegisterDTO;
import com.example.auth.domain.entity.User;
import com.example.auth.domain.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok().body(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO) {
        if(userRepository.findByLogin(registerDTO.login()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());

        User user = new User(registerDTO.login(), encryptedPassword, registerDTO.role());

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
