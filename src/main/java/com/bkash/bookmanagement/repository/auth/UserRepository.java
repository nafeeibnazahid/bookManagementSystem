package com.bkash.bookmanagement.repository.auth;

import com.bkash.bookmanagement.entity.auth.UserInfo;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends RefreshableCRUDRepository<UserInfo, Long> {
    public UserInfo findByUsername(String username);
    UserInfo findFirstById(Long id);

}