package com.bkash.bookmanagement.entity;

public class BookGenre {
    private Integer bookId;
    private Integer genreId;


    public BookGenre(Integer bookId, Integer genreId) {
        this.bookId = bookId;
        this.genreId = genreId;
    }

    public BookGenre() {
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }
}
