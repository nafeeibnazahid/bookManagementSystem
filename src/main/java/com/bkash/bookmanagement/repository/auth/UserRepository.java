package com.bkash.bookmanagement.repository.auth;

import com.bkash.bookmanagement.entity.auth.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<UserInfo, Long> {
    public UserInfo findByUsername(String username);

    UserInfo findFirstById(Long id);

}