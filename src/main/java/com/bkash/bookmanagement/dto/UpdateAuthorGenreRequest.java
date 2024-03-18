package com.bkash.bookmanagement.dto;

public class UpdateAuthorGenreRequest {
    public String getName() {
        return name;
    }


    public UpdateAuthorGenreRequest() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public UpdateAuthorGenreRequest(String name) {
        this.name = name;
    }

    private String name;
}
