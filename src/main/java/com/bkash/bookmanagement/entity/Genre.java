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
}
