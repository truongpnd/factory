package com.amitgroup.sqldatabase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.amitgroup.sqldatabase.entities.UserToken;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    Optional<UserToken> findByRefreshToken(String refreshToken);

    List<UserToken> findByUserId(Long userId);

    //find by token
    @Query("SELECT ut FROM UserToken ut WHERE ut.token = ?1")
    UserToken findByToken(String token);
}
