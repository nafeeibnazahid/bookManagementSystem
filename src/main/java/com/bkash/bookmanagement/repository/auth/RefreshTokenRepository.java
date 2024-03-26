package com.bkash.bookmanagement.repository.auth;

import com.bkash.bookmanagement.common.Constant;
import com.bkash.bookmanagement.entity.auth.RefreshToken;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Integer> {


    Optional<RefreshToken> findByToken(String token);


}