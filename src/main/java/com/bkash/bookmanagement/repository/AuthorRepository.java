package com.bkash.bookmanagement.repository;

import com.bkash.bookmanagement.entity.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository
        extends JpaRepository<Author, Integer> {
    public List<Author> findAllByOrderByIdDesc(Pageable pageable);
}
