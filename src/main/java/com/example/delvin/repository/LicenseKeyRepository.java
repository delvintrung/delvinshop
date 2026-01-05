package com.example.delvin.repository;

import com.example.delvin.entity.LicenseKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseKeyRepository extends JpaRepository<LicenseKey, Long> {
    boolean existsByKeyCode(String keyCode);
}
