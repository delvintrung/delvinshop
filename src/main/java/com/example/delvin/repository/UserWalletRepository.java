package com.example.delvin.repository;

import com.example.delvin.entity.UserWallet;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserWalletRepository extends JpaRepository<UserWallet, Long> {
    UserWallet findByUserId(Long userId);

    @Query(
            value = """
        SELECT *
        FROM user_wallets
        WHERE user_id = :userId
        FOR UPDATE
    """,
            nativeQuery = true
    )
    Optional<UserWallet> findByUserIdForUpdate(@Param("userId") Long userId);



}
