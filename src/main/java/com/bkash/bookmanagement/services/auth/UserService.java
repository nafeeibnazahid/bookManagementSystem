package com.bkash.bookmanagement.services.auth;


import com.bkash.bookmanagement.dto.auth.UserRequest;
import com.bkash.bookmanagement.dto.auth.UserResponse;

import java.util.List;


public interface UserService {

    UserResponse saveUser(UserRequest userRequest);

    UserResponse getUser();

    List<UserResponse> getAllUser();
}
