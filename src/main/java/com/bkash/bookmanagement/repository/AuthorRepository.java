package com.bkash.bookmanagement.repository;

import com.bkash.bookmanagement.entity.Author;
import com.bkash.bookmanagement.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository
        extends JpaRepository<Author, Integer> {
    public List<Author> findAllByOrderByIdDesc(Pageable pageable);

    @Query(value = "select * from author a where " +
            "(:id is NULL or a.id = :id) and " +
            "(:name is NULL or a.name = :name) and " +
            "(cast(:bookId as integer) is NULL or a.id in (select BA.author_id from book_author BA where BA.book_id = :bookId) ) " +
            "order by id desc " +
            "offset :offset " +
            "limit :limit", nativeQuery = true)
    List<Author> getAuthor(
            @Param("id") Optional<Integer> id,
            @Param("name") Optional<String> name,
            @Param("bookId") Optional<Integer> bookId,
            @Param("offset") Integer offset,
            @Param("limit") Integer limit
    );

}
