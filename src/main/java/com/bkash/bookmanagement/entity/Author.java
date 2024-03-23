package com.bkash.bookmanagement.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Author {
    @Id
    @SequenceGenerator(
            name = "author_id_sequence",
            sequenceName = "author_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "author_id_sequence"
    )
//    @Column(name = "ID")
    private Integer id;


    @Size(min = 1)
    @Column(unique = true, nullable = false)
    private String name;

    public Author() {

    }

    public Author(
            Integer id,
            String name
    ) {
        this.id = id;
        this.name = name;
//        this.booksWritten = booksWritten;
    }


//    @ManyToMany
//    @JoinTable(
//            name = "book_author",
//            joinColumns = @JoinColumn(name = "book_id"),
//            inverseJoinColumns = @JoinColumn(name = "author_id"))
//    @JsonManagedReference
//    private Set<Book> booksWritten;

    @Transient
    private List<Book> bookList;

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

//    public Set<Book> getBooksWritten() {
//        return booksWritten;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public void setBooksWritten(Set<Book> booksWritten) {
//        this.booksWritten = booksWritten;
//    }
}
