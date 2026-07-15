package com.documentvault.backend.security.currentuser;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.documentvault.backend.entity.User;
import com.documentvault.backend.repository.UserRepository;
import com.documentvault.backend.security.userdetails.UserPrincipal;

@Service
public class CurrentUserServiceImpl implements CurrentUserService{
    private final UserRepository userRepository;

    public CurrentUserServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public User getCurrentUser(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal=(UserPrincipal)authentication.getPrincipal();
        return userRepository.findByEmail(principal.getUsername()).orElseThrow(() -> new IllegalArgumentException("user not found."));
    }
}