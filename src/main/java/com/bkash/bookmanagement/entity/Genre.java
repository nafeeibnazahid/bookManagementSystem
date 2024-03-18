package com.bkash.bookmanagement.entity;

import jakarta.persistence.*;

@Entity
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

    @Column(unique = true)
    private String name;

    public Genre(
            Integer id,
            String name
    ) {
        this.id = id;
        this.name = name;
    }

    public Genre() {
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
