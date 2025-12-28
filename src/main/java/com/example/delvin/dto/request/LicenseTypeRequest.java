package com.example.delvin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Setter
@Getter
public class LicenseTypeRequest {
    private Long id;
    private String typeName;
}
