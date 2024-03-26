package com.bkash.bookmanagement.services.auth;

import com.bkash.bookmanagement.common.Constant;
import com.bkash.bookmanagement.entity.auth.RefreshToken;
import com.bkash.bookmanagement.repository.auth.RefreshTokenRepository;
import com.bkash.bookmanagement.repository.auth.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.bkash.bookmanagement.common.Constant.fiveSecondsInMilliSeconds;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Cacheable(cacheNames = {Constant.refreshTokenCache}, key = "#refreshToken.token")
    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken createRefreshToken(String username) {
        long fiveMinutesInMilliSeconds = 10 * 60 * 1000;
        RefreshToken refreshToken = RefreshToken.builder()
                .userInfo(userRepository.findByUsername(username))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(fiveMinutesInMilliSeconds)) // set expiry of refresh token to 10 minutes - you can configure it application.properties file
                .build();
        return save(refreshToken);
    }

    @Cacheable(cacheNames = {Constant.refreshTokenCache}, key = "#token")
    public Optional<RefreshToken> findByToken(String token) {
        try {
            System.out.println("------------- Going to sleep for 5 seconds to simulate Backend Delay -----------");
            Thread.sleep(fiveSecondsInMilliSeconds);
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }

        return refreshTokenRepository.findByToken(token);
    }

    @CacheEvict(cacheNames = {Constant.refreshTokenCache}, key = "#refreshToken.token")
    public void delete(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException(refreshToken.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return refreshToken;
    }

}