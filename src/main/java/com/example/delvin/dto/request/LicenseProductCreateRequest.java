package com.example.delvin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Data
@AllArgsConstructor
@Getter
@Setter
public class LicenseProductCreateRequest {
    private String name;
    private String description;
    private Double price;
    private Instant expireAt;
}
