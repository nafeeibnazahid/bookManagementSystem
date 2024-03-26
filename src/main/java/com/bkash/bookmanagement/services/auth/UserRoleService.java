package com.bkash.bookmanagement.services.auth;

import com.bkash.bookmanagement.entity.auth.UserRole;

public interface UserRoleService {
    UserRole saveUserRole(UserRole userRole);

    public UserRole findByName(String name);

    public UserRole findById(Long id);
}
