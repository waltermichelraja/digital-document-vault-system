package com.documentvault.backend.service.admin;

import java.util.List;
import java.util.stream.Collectors;

import com.documentvault.backend.dto.DashboardResponse;
import com.documentvault.backend.dto.UpdateRoleRequest;
import com.documentvault.backend.entity.Role;
import com.documentvault.backend.entity.User;
import com.documentvault.backend.repository.DocumentRepository;
import com.documentvault.backend.security.currentuser.CurrentUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.documentvault.backend.dto.UserResponse;
import com.documentvault.backend.repository.UserRepository;

@Service
public class AdminServiceImpl implements AdminService{
    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;
    private final DocumentRepository documentRepository;
    private static final Logger logger=LoggerFactory.getLogger(AdminServiceImpl.class);

    public AdminServiceImpl(UserRepository userRepository,CurrentUserService currentUserService,DocumentRepository documentRepository){
        this.userRepository=userRepository;
        this.currentUserService=currentUserService;
        this.documentRepository=documentRepository;
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

    @Override
    public UserResponse updateUserRole(String userId,UpdateRoleRequest request){
        User currentAdmin=currentUserService.getCurrentUser();
        User user=userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("user not found."));

        if(user.getId().equals(currentAdmin.getId())){
            throw new IllegalArgumentException("you cannot change your own role.");
        }
        Role oldRole=user.getRole();
        user.setRole(request.getRole());
        userRepository.save(user);
        logger.info(
                "admin '{}' changed role of '{}' from '{}' to '{}'.",
                currentAdmin.getEmail(),
                user.getEmail(),
                oldRole,
                user.getRole()
        );
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole()
        );
    }

    @Override
    public DashboardResponse getDashboard(){
        return new DashboardResponse(
                userRepository.count(),
                userRepository.countByRole(Role.ADMIN),
                userRepository.countByRole(Role.MANAGER),
                userRepository.countByRole(Role.EMPLOYEE),
                documentRepository.count()
        );
    }
}