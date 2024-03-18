package com.bkash.bookmanagement.repository;

import com.bkash.bookmanagement.entity.Genre;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository
    extends JpaRepository<Genre, Integer> {
    public List<Genre> findAllByOrderByIdDesc(Pageable pageable);
}
