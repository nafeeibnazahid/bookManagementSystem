package com.bkash.bookmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
//    @Column(name = "ID")
    private Integer id;

    @Column(unique = true)
    private String name;
    private Timestamp createdAt;

//    @ManyToMany
//    @JoinTable(
//            name="book_author",
//            joinColumns = @JoinColumn(name = "author_id"),
//            inverseJoinColumns = @JoinColumn(name = "book_id")
//    )
//    @JsonBackReference
//    private Set<Author> authorSet;

    public Book(Integer id, String name, Timestamp createdAt, Set<Author> authorSet) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
//        this.authorSet = authorSet;
    }

    public Book() {

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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

//    public Set<Author> getAuthorSet() {
//        return authorSet;
//    }
//
//    public void setAuthorSet(Set<Author> authorSet) {
//        this.authorSet = authorSet;
//    }
}
