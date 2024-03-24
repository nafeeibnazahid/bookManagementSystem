package com.bkash.bookmanagement.repository;

import com.bkash.bookmanagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookRepository
        extends JpaRepository<Book, Integer> {



//    List<Book> findBookByNameLikeOrderById(String likePattern);

    Book findByName(String name);

    @Query(
            value = "select * from book b where " +
            " (:id is NULL or b.id = :id) and " +
            " (cast(:startTime as timestamp) is NULL or :startTime <= b.created_at) and " +
            " (cast(:endTime as timestamp) is NULL or b.created_at <= :endTime ) and " +
            " (:partialName is NULL or b.name like :partialName) and " +
            " (cast(:authorId as integer) is NULL or b.id in (select BA.book_id from book_author BA where BA.author_id = :authorId) ) and " +
            " (cast(:genreId as integer) is NULL or b.id in (select BG.book_id from book_genre BG where BG.genre_id = :genreId) ) " +
            " order by id desc " +
            " offset :offset " +
            " limit :limit ",
            nativeQuery = true
    )
    List<Book> getBooks(
            @Param("id") Optional<Integer> id,
            @Param("startTime") Optional<Date> startTime,
            @Param("endTime") Optional<Date> endTime,
            @Param("partialName") Optional<String> partialName,
            @Param("authorId") Optional<Integer> authorId,
            Optional<Integer> genreId,
            @Param("offset") Integer offset,
            @Param("limit") Integer limit
    );

}

