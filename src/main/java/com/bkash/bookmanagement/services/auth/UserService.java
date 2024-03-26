package com.bkash.bookmanagement.services.auth;


import com.bkash.bookmanagement.dto.auth.NewUserSaveRequest;
import com.bkash.bookmanagement.dto.auth.UserResponse;
import com.bkash.bookmanagement.entity.auth.UserInfo;
import com.bkash.bookmanagement.entity.auth.UserRole;

import java.util.List;
import java.util.Set;


public interface UserService {

    UserResponse saveUser(NewUserSaveRequest userRequest);

    UserResponse getUser();

    List<UserResponse> getAllUser();

    public UserInfo findFirstById(Long userId);

    public UserInfo updateRoles(UserInfo userInfo, Set<UserRole> roles);

}
