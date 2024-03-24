package com.bkash.bookmanagement.dto.auth;

import jakarta.validation.constraints.NotNull;

public class AddAuthorToABookRequest {
    @NotNull
    public Integer authorId;
}
