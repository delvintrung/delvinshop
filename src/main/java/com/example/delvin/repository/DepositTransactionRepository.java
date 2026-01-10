package com.example.delvin.repository;

import com.example.delvin.entity.DepositTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface DepositTransactionRepository extends JpaRepository<DepositTransaction, Long> {

    Optional<DepositTransaction> findByOrderCode(String orderCode);
}
