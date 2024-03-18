package com.bkash.bookmanagement.repository;

import com.bkash.bookmanagement.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

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
}

