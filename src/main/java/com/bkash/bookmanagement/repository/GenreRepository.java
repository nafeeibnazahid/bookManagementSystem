package com.bkash.bookmanagement.repository;

import com.bkash.bookmanagement.entity.Genre;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface GenreRepository
        extends JpaRepository<Genre, Integer> {
    public List<Genre> findAllByOrderByIdDesc(Pageable pageable);

    @Query(value = "select * from Genre g where " +
            "(:id is NULL or g.id = :id) and " +
            "(:name is NULL or g.name = :name) and " +
            "(cast(:bookId as integer) is NULL or g.id in (select BG.genre_id from book_genre BG where BG.book_id = :bookId) ) " +
            "order by id desc " +
            "offset :offset " +
            "limit :limit", nativeQuery = true)
    List<Genre> getGenre(
            @Param("id") Optional<Integer> id,
            @Param("name") Optional<String> name,
            @Param("bookId") Optional<Integer> bookId,
            @Param("offset") Integer offset,
            @Param("limit") Integer limit
    );


}
