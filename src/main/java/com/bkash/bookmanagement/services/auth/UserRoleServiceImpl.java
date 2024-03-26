package com.bkash.bookmanagement.services.auth;

import com.bkash.bookmanagement.entity.auth.UserRole;
import com.bkash.bookmanagement.repository.auth.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserRole saveUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @Override
    public UserRole findByName(String name) {
        return userRoleRepository.findByName(name);
    }

    @Override
    public UserRole findById(Long id) {
        return userRoleRepository.findById(id).get();
    }
}
