package com.bkash.bookmanagement.dto.auth;

import com.bkash.bookmanagement.entity.auth.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewUserSaveRequest {
    private Long id;
    private String username;
    private String password;
    private Set<UserRole> roles;
}
