package com.bkash.bookmanagement.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
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
    @Column(name = "ID")
    private Integer id;

    @Column(unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Book> booksWritten;
}
