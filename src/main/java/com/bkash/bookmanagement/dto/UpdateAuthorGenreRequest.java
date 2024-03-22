package com.bkash.bookmanagement.dto;

public class UpdateAuthorGenreRequest {
    private String name;


    public UpdateAuthorGenreRequest() {
    }

    public UpdateAuthorGenreRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
