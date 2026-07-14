package com.documentvault.backend.auth.service;

import org.springframework.stereotype.Service;

import com.documentvault.backend.auth.dto.AuthResponse;
import com.documentvault.backend.auth.dto.RegisterRequest;
import com.documentvault.backend.entity.Role;
import com.documentvault.backend.entity.User;
import com.documentvault.backend.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public AuthResponse register(RegisterRequest request){

        if(request.getFullName()==null||request.getFullName().isBlank()){
            throw new IllegalArgumentException("full name cannot be empty.");
        }

        if(request.getEmail()==null||request.getEmail().isBlank()){
            throw new IllegalArgumentException("email cannot be empty.");
        }

        if(request.getPassword()==null||request.getPassword().isBlank()){
            throw new IllegalArgumentException("password cannot be empty.");
        }

        if(userRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("email already exists.");
        }

        User user=new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());

        // bcrypt comes in next step
        user.setPassword(request.getPassword());

        user.setRole(Role.EMPLOYEE);

        userRepository.save(user);

        return new AuthResponse(
                true,
                "user registered successfully."
        );
    }
}