package com.example.delvin.repository;

import com.example.delvin.entity.PriceLicenseKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceLicenseKeyRepository extends JpaRepository<PriceLicenseKey, Long> {
}
