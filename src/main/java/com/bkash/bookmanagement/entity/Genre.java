package com.bkash.bookmanagement.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import javax.validation.constraints.Size;
import java.util.List;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Genre {
    @Id
    @SequenceGenerator(
            name = "genre_id_sequence",
            sequenceName = "genre_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "genre_id_sequence"
    )
    private Integer id;

    @Size(min = 1)
    @Column(unique = true, nullable = false)
    private String name;

    @Transient
    List<Book> bookList;

    public Genre(
            Integer id,
            String name
    ) {
        this.id = id;
        this.name = name;
    }

    public Genre() {
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
