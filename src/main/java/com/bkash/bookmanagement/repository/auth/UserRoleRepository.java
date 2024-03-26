package com.bkash.bookmanagement.repository.auth;

import com.bkash.bookmanagement.entity.auth.UserInfo;
import com.bkash.bookmanagement.entity.auth.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    public UserRole findByName(String name);
}
