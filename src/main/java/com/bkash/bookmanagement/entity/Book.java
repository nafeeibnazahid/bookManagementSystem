package com.bkash.bookmanagement.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.sql.Timestamp;
import java.util.List;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @Size(min = 1)
    @Column(unique = true, nullable = false)
    @NotBlank(message = "name can't be empty")
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;


//    @ManyToMany
//    @JoinTable(
//            name="book_author",
//            joinColumns = @JoinColumn(name = "author_id"),
//            inverseJoinColumns = @JoinColumn(name = "book_id")
//    )
//    @JsonBackReference
    @Transient
    private List<Author> authorList;

    @Transient
    private List<Genre> genreList;

    public Book(
            Integer id,
            String name,
            Timestamp createdAt
    ) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public Book() {

    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
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

    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }


}
