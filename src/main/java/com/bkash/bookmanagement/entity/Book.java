package com.bkash.bookmanagement.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Set;

@Entity
public class Book {
    @Id
    @SequenceGenerator(
            name = "book_id_sequence",
            sequenceName = "book_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_id_sequence"
    )
    @Column(name = "ID")
    private Integer id;

    @Column(unique = true)
    private String name;
    private Timestamp createdAt;

    @ManyToMany
    @JoinTable(
            name="book_author",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Author> authorSet;
}
