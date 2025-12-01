package com.example.delvin.repository;

import com.example.delvin.entity.LicenseProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseProductRepository  extends JpaRepository<LicenseProduct, Long> {
}
