package com.bkash.bookmanagement.repository;

import com.bkash.bookmanagement.entity.Genre;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GenreRepository
    extends JpaRepository<Genre, Integer> {
    public List<Genre> findAllByOrderByIdDesc(Pageable pageable);

//    @Query("select g from Genre g where (?1 is None or g.name = ?1)")
//    Genre findByName(String genreName);

//    @Query(value = "select g from Genre g where (g.name = :genreName or :genreName is NULL) offset :offset limit :limit", nativeQuery = true)
    @Query(value = "select * from Genre g where (g.name = :genreName or :genreName is NULL) and (:id is null or g.id = :id) offset :offset limit :limit", nativeQuery = true)
    List<Genre> findByNameId(
            @Param("id") Optional<Integer> id,
            @Param("genreName") String genreName,
            @Param("offset") Integer offset,
            @Param("limit") Integer limit);


    @Query(value = "select * from Genre g where " +
            " (:id is NULL or g.id = :id) and " +
            "(:name is NULL or g.name = :name) " +
            "order by id desc", nativeQuery = true)
    List<Genre> bitByBitQuery(
            @Param("id") Optional<Integer> id,
            @Param("name") Optional<String> name
//            @Param("offset") Integer offset,
//            @Param("limit") Integer limit
    );


}
