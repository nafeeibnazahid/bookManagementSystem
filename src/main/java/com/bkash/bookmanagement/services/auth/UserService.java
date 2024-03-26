package com.bkash.bookmanagement.services.auth;


import com.bkash.bookmanagement.dto.auth.UserSaveRequest;
import com.bkash.bookmanagement.dto.auth.UserResponse;

import java.util.List;


public interface UserService {

    UserResponse saveUser(UserSaveRequest userRequest);

    UserResponse getUser();

    List<UserResponse> getAllUser();
}
