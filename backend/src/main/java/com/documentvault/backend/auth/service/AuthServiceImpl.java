package com.documentvault.backend.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.documentvault.backend.auth.dto.AuthResponse;
import com.documentvault.backend.auth.dto.LoginRequest;
import com.documentvault.backend.auth.dto.RegisterRequest;
import com.documentvault.backend.entity.Role;
import com.documentvault.backend.entity.User;
import com.documentvault.backend.repository.UserRepository;
import com.documentvault.backend.security.jwt.JwtService;

@Service
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private static final Logger logger=LoggerFactory.getLogger(AuthServiceImpl.class);

    public AuthServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder,JwtService jwtService){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtService=jwtService;
    }

    @Override
    public AuthResponse register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("email already exists.");
        }
        User user=new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.EMPLOYEE);
        userRepository.save(user);
        logger.info("user '{}' registered successfully.",user.getEmail());
        return new AuthResponse(true,"user registered successfully.");
    }

    @Override
    public AuthResponse login(LoginRequest request){
        User user=userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("invalid email or password."));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("invalid email or password.");
        }
        String token=jwtService.generateToken(user.getEmail());
        AuthResponse response=new AuthResponse(true,"login successful.",token);
        response.setFullName(user.getFullName());
        response.setRole(user.getRole());
        logger.info("user '{}' logged in successfully.",user.getEmail());
        return response;
    }
}