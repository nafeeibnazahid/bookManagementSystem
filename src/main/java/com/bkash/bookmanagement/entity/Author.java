package com.bkash.bookmanagement.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Author {
//    TODO : write some auto code which would inserts some Authors

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

    public Author() {

    }

    public Author(Integer id, String name) {
        this.id = id;
        this.name = name;
//        this.booksWritten = booksWritten;
    }

    @Column(unique = true)
    private String name;



//    @ManyToMany
//    @JoinTable(
//            name = "book_author",
//            joinColumns = @JoinColumn(name = "book_id"),
//            inverseJoinColumns = @JoinColumn(name = "author_id"))
//    @JsonManagedReference
//    private Set<Book> booksWritten;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

//    public Set<Book> getBooksWritten() {
//        return booksWritten;
//    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public void setBooksWritten(Set<Book> booksWritten) {
//        this.booksWritten = booksWritten;
//    }
}
