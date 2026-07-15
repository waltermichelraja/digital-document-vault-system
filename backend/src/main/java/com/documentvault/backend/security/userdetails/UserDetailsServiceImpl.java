package com.documentvault.backend.security.userdetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.documentvault.backend.entity.User;
import com.documentvault.backend.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException{
        User user=userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found."));
        return new UserPrincipal(user);
    }

}