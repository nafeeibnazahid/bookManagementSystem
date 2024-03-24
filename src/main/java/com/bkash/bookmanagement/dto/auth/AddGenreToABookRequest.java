package com.bkash.bookmanagement.dto.auth;

import jakarta.validation.constraints.NotNull;

public class AddGenreToABookRequest {
    @NotNull
    public Integer genreId;
}
