package com.bkash.bookmanagement.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class UserUpdateRequest {
    @NotNull
    public Long id;

    @NotNull
    public Set<Integer> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Integer> getRoles() {
        return roles;
    }

    public void setRoles(Set<Integer> roles) {
        this.roles = roles;
    }
}
