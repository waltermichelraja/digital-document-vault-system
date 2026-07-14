package com.documentvault.backend.service.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.documentvault.backend.dto.UserResponse;
import com.documentvault.backend.repository.UserRepository;

@Service
public class AdminServiceImpl implements AdminService{
    private final UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public List<UserResponse> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getFullName(),
                        user.getEmail(),
                        user.getRole()
                ))
                .collect(Collectors.toList());
    }
}