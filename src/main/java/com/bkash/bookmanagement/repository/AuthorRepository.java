package com.bkash.bookmanagement.repository;

import com.bkash.bookmanagement.entity.Author;
import com.bkash.bookmanagement.entity.Genre;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository
        extends JpaRepository<Author, Integer> {
    public List<Author> findAllByOrderByIdDesc(Pageable pageable);

    @Query(value = "select * from book b where " +
            "(:id is NULL or b.id = :id) and " +
            "(:name is NULL or b.name = :name) " +
            "order by id desc " +
            "offset :offset " +
            "limit :limit", nativeQuery = true)
    List<Genre> getGenre(
            @Param("id") Optional<Integer> id,
            @Param("name") Optional<String> name,
            @Param("offset") Integer offset,
            @Param("limit") Integer limit
    );

}
