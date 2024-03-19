package com.bkash.bookmanagement.dto;

import com.bkash.bookmanagement.entity.Author;
import com.bkash.bookmanagement.entity.Book;
import com.bkash.bookmanagement.entity.Genre;

import java.util.ArrayList;
import java.util.List;

public class AddBookRequest {
    private Book book;
    public List<Integer> authorIdList;
    public List<Integer> genreIdList;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<Integer> getAuthorIdList() {
        return authorIdList;
    }

    public void setAuthorIdList(List<Integer> authorIdList) {
        this.authorIdList = authorIdList;
    }

    public List<Integer> getGenreIdList() {
        return genreIdList;
    }

    public void setGenreIdList(List<Integer> genreIdList) {
        this.genreIdList = genreIdList;
    }
}