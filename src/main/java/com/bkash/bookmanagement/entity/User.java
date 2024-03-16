//package com.bkash.bookmanagement.entity;
//
//import jakarta.persistence.*;
//
//@Entity
//public class User {
//    @Id
//    @SequenceGenerator(
//            name = "user_id_sequence",
//            sequenceName = "user_id_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "user_id_sequence"
//    )
//    @Column(name = "ID")
//    private Integer id;
//
//    @Column(unique = true)
//    private String username;
//
//    private String salt;
//    private String hash;
//}
