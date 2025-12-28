package com.example.delvin.repository;

import com.example.delvin.entity.LicenseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseTypeRepository extends JpaRepository<LicenseType, Long> {
    boolean existsByTypeName(String typeName);
}
