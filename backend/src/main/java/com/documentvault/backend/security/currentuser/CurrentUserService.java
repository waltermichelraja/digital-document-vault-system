package com.documentvault.backend.security.currentuser;

import com.documentvault.backend.entity.User;

public interface CurrentUserService{
    User getCurrentUser();
}