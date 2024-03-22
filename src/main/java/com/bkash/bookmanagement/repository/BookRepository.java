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

    // TODO : need to check LIKE name query working properly or not
//    @Query(//            " (:prefixName is NULL or name is LIKE :prefixName) and " +
//            """
//                     select book from book where  (:prefixName is NULL or name is like :prefixName)  order by id desc \
//                    """)
//    @Query("select * from youth_account where id <= :startId and phone_number = :phoneNumber order by id desc limit :pageSize")
//    List<Book> getBooks(String prefixName);


//    @Query("select b from book where b.name LIKE :prefixName || '%'")
//    List<Book> getSearchedBook(String prefixName);

    //    TODO : need to handle likePattern + "%"
    List<Book> findBookByNameLikeOrderById(String likePattern);

    @Query(
            value = "select * from book b where " +
            "(:id is NULL or b.id = :id) and " +
            "(cast(:startTime as timestamp) is NULL or :startTime <= b.created_at) and " +
            "(cast(:endTime as timestamp) is NULL or b.created_at <= :endTime ) and " +
            "(:partialName is NULL or b.name like :partialName) " +
            "order by id desc " +
            "offset :offset " +
            "limit :limit ",
            nativeQuery = true
    )
    List<Book> getBooks(
            @Param("id") Optional<Integer> id,
            @Param("startTime") Optional<Date> startTime,
            @Param("endTime") Optional<Date> endTime,
            @Param("partialName") Optional<String> partialName,
            @Param("offset") Integer offset,
            @Param("limit") Integer limit
    );

}

